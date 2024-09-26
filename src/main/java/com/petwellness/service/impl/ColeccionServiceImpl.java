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
        Usuario usuario = usuarioRepository.findById(coleccionDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Coleccion coleccion = new Coleccion();
        coleccion.setNombre(coleccionDTO.getNombre());
        coleccion.setUsuario(usuario);
        coleccion.setProductosColeccion(new HashSet<>());
        
        coleccion = coleccionRepository.save(coleccion);
        return mapToDTO(coleccion);
    }

    @Override
    @Transactional
    public ColeccionDTO actualizarColeccion(Integer id, ColeccionDTO coleccionDTO) {
        Coleccion coleccion = coleccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colección no encontrada"));
        coleccion.setNombre(coleccionDTO.getNombre());
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

        boolean productoExiste = productoColeccionRepository.existsByColeccionIdAndProductoIdProducto(coleccionId, productoId);

        if (!productoExiste) {
            ProductoColeccion productoColeccion = new ProductoColeccion();
            productoColeccion.setColeccion(coleccion);
            productoColeccion.setProducto(producto);
            productoColeccionRepository.save(productoColeccion);
        }

        return mapToDTO(coleccion);
    }

    @Override
    @Transactional
    public void eliminarProductoDeColeccion(Integer coleccionId, Integer productoId) {
        productoColeccionRepository.deleteByColeccionIdAndProductoIdProducto(coleccionId, productoId);
    }
    
    private ColeccionDTO mapToDTO(Coleccion coleccion) {
        ColeccionDTO dto = new ColeccionDTO();
        dto.setId(coleccion.getId());
        dto.setNombre(coleccion.getNombre());
        dto.setUsuarioId(coleccion.getUsuario().getUserId());
        
        List<Integer> productosIds = productoColeccionRepository.findProductIdsByColeccionId(coleccion.getId());
        dto.setProductosIds(new HashSet<>(productosIds));
        
        return dto;
    }
}