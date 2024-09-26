package com.petwellness.service.impl;

import com.petwellness.dto.ColeccionDTO;
import com.petwellness.model.entity.Coleccion;
import com.petwellness.model.entity.ProductoColeccion;
import com.petwellness.model.entity.Producto;
import com.petwellness.repository.ColeccionRepository;
import com.petwellness.repository.ProductoColeccionRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColeccionServiceImpl implements ColeccionService {

    private final ColeccionRepository coleccionRepository;
    private final ProductoColeccionRepository productoColeccionRepository;
    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ColeccionDTO> obtenerColeccionesDeUsuario(Integer userId) {
        return coleccionRepository.findByUsuarioUserId(userId).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ColeccionDTO crearColeccion(ColeccionDTO coleccionDTO) {
        Coleccion coleccion = mapearAEntidad(coleccionDTO);
        coleccion = coleccionRepository.save(coleccion);
        return mapearADTO(coleccion);
    }

    @Override
    @Transactional
    public ColeccionDTO actualizarColeccion(Integer id, ColeccionDTO coleccionDTO) {
        Coleccion coleccion = coleccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
        coleccion.setNombre(coleccionDTO.getNombre());
        coleccion = coleccionRepository.save(coleccion);
        return mapearADTO(coleccion);
    }

    @Override
    @Transactional
    public void eliminarColeccion(Integer id) {
        Coleccion coleccion = coleccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
        if (!coleccion.getProductosColeccion().isEmpty()) {
            throw new RuntimeException("No se puede eliminar una colección con productos");
        }
        coleccionRepository.delete(coleccion);
    }

    @Override
    @Transactional
    public ColeccionDTO agregarProductoAColeccion(Integer coleccionId, Integer productoId) {
        Coleccion coleccion = coleccionRepository.findById(coleccionId)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ProductoColeccion productoColeccion = new ProductoColeccion();
        productoColeccion.setColeccion(coleccion);
        productoColeccion.setProducto(producto);
        productoColeccionRepository.save(productoColeccion);

        return mapearADTO(coleccion);
    }

    @Override
    @Transactional
    public void eliminarProductoDeColeccion(Integer coleccionId, Integer productoId) {
        productoColeccionRepository.deleteByColeccionIdAndProductoIdProducto(coleccionId, productoId);
    }

    private ColeccionDTO mapearADTO(Coleccion coleccion) {
        ColeccionDTO dto = modelMapper.map(coleccion, ColeccionDTO.class);
        dto.setProductosIds(coleccion.getProductosColeccion().stream()
                .map(pc -> pc.getProducto().getIdProducto())
                .collect(Collectors.toSet()));
        return dto;
    }

    private Coleccion mapearAEntidad(ColeccionDTO dto) {
        return modelMapper.map(dto, Coleccion.class);
    }
}