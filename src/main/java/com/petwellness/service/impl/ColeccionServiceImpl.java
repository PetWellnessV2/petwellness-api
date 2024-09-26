package com.petwellness.service.impl;

import com.petwellness.dto.ColeccionDTO;
import com.petwellness.model.entity.Coleccion;
import com.petwellness.model.entity.ProductoColeccion;
import com.petwellness.model.entity.Producto;
import com.petwellness.model.entity.Usuario;
import com.petwellness.repository.ColeccionRepository;
import com.petwellness.repository.ProductoColeccionRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.repository.UsuarioRepository;

import com.petwellness.service.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColeccionServiceImpl implements ColeccionService {


    private final ColeccionRepository coleccionRepository;
    private final ProductoColeccionRepository productoColeccionRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ColeccionDTO> obtenerColeccionesDeUsuario(Integer userId) {
        List<Coleccion> colecciones = coleccionRepository.findByUsuarioUserId(userId);
        return colecciones.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ColeccionDTO crearColeccion(ColeccionDTO coleccionDTO) {
        Coleccion coleccion = mapToEntity(coleccionDTO);
        coleccion.setProductosColeccion(new HashSet<>());
        coleccion = coleccionRepository.save(coleccion);
        return mapToDTO(coleccion);
    }

    @Override
    @Transactional
    public ColeccionDTO actualizarColeccion(Integer id, ColeccionDTO coleccionDTO) {
        Coleccion coleccion = coleccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
        updateEntityFromDTO(coleccion, coleccionDTO);
        coleccion = coleccionRepository.save(coleccion);
        return mapToDTO(coleccion);
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

        boolean productoExiste = coleccion.getProductosColeccion().stream()
                .anyMatch(pc -> pc.getProducto().getIdProducto().equals(productoId));

        if (!productoExiste) {
            ProductoColeccion productoColeccion = new ProductoColeccion();
            productoColeccion.setColeccion(coleccion);
            productoColeccion.setProducto(producto);
            coleccion.getProductosColeccion().add(productoColeccion);
            coleccionRepository.save(coleccion);
        }

        return mapToDTO(coleccion);
    }

    @Override
    @Transactional
    public void eliminarProductoDeColeccion(Integer coleccionId, Integer productoId) {
        productoColeccionRepository.deleteByColeccionIdAndProductoIdProducto(coleccionId, productoId);
    }

    private ColeccionDTO mapToDTO(Coleccion coleccion) {
        ColeccionDTO dto = modelMapper.map(coleccion, ColeccionDTO.class);
        dto.setUsuarioId(coleccion.getUsuario().getUserId());
        if (coleccion.getProductosColeccion() != null) {
            dto.setProductosIds(coleccion.getProductosColeccion().stream()
                    .map(pc -> pc.getProducto().getIdProducto())
                    .collect(Collectors.toSet()));
        } else {
            dto.setProductosIds(new HashSet<>());
        }
        return dto;
    }

    private Coleccion mapToEntity(ColeccionDTO dto) {
        Coleccion coleccion = modelMapper.map(dto, Coleccion.class);
        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            coleccion.setUsuario(usuario);
        }
        return coleccion;
    }

    private void updateEntityFromDTO(Coleccion coleccion, ColeccionDTO dto) {
        coleccion.setNombre(dto.getNombre());
    }
}