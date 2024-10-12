package com.petwellness.service.impl;

import com.petwellness.dto.ColeccionDTO;
import com.petwellness.mapper.ColeccionMapper;
import com.petwellness.model.entity.Coleccion;
import com.petwellness.model.entity.ProductoColeccion;
import com.petwellness.model.entity.Producto;
import com.petwellness.repository.ColeccionRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColeccionServiceImpl implements ColeccionService {

    private final ColeccionRepository coleccionRepository;
    private final ProductoRepository productoRepository;
    private final ColeccionMapper coleccionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ColeccionDTO> obtenerTodasLasColecciones() {
        List<Coleccion> colecciones = coleccionRepository.findAll();
        return colecciones.stream()
                .map(coleccionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ColeccionDTO> obtenerColeccionesDeUsuario(Integer userId) {
        List<Coleccion> colecciones = coleccionRepository.findByUsuarioId(userId);
        return colecciones.stream()
                .map(coleccionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ColeccionDTO crearColeccion(ColeccionDTO coleccionDTO) {
        Coleccion coleccion = coleccionMapper.toEntity(coleccionDTO);
        coleccion = coleccionRepository.save(coleccion);
        return coleccionMapper.toDTO(coleccion);
    }

    @Override
    @Transactional
    public ColeccionDTO actualizarColeccion(Integer id, ColeccionDTO coleccionDTO) {
        Coleccion coleccion = coleccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
        coleccion.setNombre(coleccionDTO.getNombre());
        coleccion = coleccionRepository.save(coleccion);
        return coleccionMapper.toDTO(coleccion);
    }

    @Override
    @Transactional
    public ColeccionDTO agregarProductoAColeccion(Integer coleccionId, Integer productoId) {
        Coleccion coleccion = coleccionRepository.findById(coleccionId)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
        
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        boolean productoYaExiste = coleccion.getProductosColeccion().stream()
                .anyMatch(pc -> pc.getProducto().getIdProducto().equals(productoId));

        if (!productoYaExiste) {
            ProductoColeccion productoColeccion = new ProductoColeccion();
            productoColeccion.setColeccion(coleccion);
            productoColeccion.setProducto(producto);
            coleccion.getProductosColeccion().add(productoColeccion);
            coleccion = coleccionRepository.save(coleccion);
        }

        return coleccionMapper.toDTO(coleccion);
    }


    @Override
    @Transactional
    public void eliminarProductoDeColeccion(Integer coleccionId, Integer productoId) {
        Coleccion coleccion = coleccionRepository.findById(coleccionId)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));

        coleccion.getProductosColeccion().removeIf(pc -> pc.getProducto().getIdProducto().equals(productoId));
        coleccionRepository.save(coleccion);
    }

    @Override
    @Transactional
    public void eliminarColeccion(Integer id) {
        coleccionRepository.deleteById(id);
    }
}