package com.petwellness.service.impl;

import com.petwellness.dto.MascotaProfileDTO;
import com.petwellness.dto.ProductoDTO;
import com.petwellness.model.entity.Mascota;
import com.petwellness.model.entity.Producto;
import com.petwellness.model.enums.Especie;
import com.petwellness.repository.CategoriaProductoRepository;
import com.petwellness.repository.MascotaRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.RecomendacionService;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.model.entity.CategoriaProducto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecomendacionServiceImpl implements RecomendacionService {

    private final MascotaRepository mascotaRepository;
    private final ProductoRepository productoRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductoDTO> getRecomendaciones(Integer mascotaId) {
        // 1. Obtener la mascota por su ID
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(ResourceNotFoundException::new);

        // 2. Obtener la especie de la mascota
        Especie especie = mascota.getEspecie();

        // 3. Obtener los tipos de producto recomendados para la especie
        List<CategoriaProducto> tiposProducto = getTipoProductosPorEspecie(especie);

        // 4. Obtener los productos basados en los tipos de producto
        List<Producto> productos = productoRepository.findByCategoriaProductoIn(tiposProducto);

        // 5. Si no hay productos, obtener productos generales
        if (productos.isEmpty()) {
            productos = productoRepository.findAll();
        }

        // 6. Mapear productos a DTOs
        List<ProductoDTO> productosDTO = productos.stream()
                .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                .collect(Collectors.toList());

        return productosDTO;
    }

    private MascotaProfileDTO getMascotaById(Integer mascotaId) {
        return mascotaRepository.findById(mascotaId)
                .map(mascota -> modelMapper.map(mascota, MascotaProfileDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID " + mascotaId + " no existe."));
    }

    private List<CategoriaProducto> getTipoProductosPorEspecie(Especie especie) {
        List<CategoriaProducto> tipos = new ArrayList<>();
        switch (especie) {
            case PERRO:
                categoriaProductoRepository.findByName("Alimentos")
                        .ifPresent(tipos::add);
                categoriaProductoRepository.findByName("Juguetes")
                        .ifPresent(tipos::add);
                break;
            case GATO:
                categoriaProductoRepository.findByName("Alimentos")
                        .ifPresent(tipos::add);
                categoriaProductoRepository.findByName("Juguetes")
                        .ifPresent(tipos::add);
                break;
            default:
                break;
        }
        return tipos;
    }

    @Override
    public <Producto> List<Producto> getRecomendaciones(Long mascotaId) {
        return List.of();
    }

    @Override
    @Transactional()
    public List<ProductoDTO> getAllRecomendaciones() {
        // 1. Obtener todos los productos desde la base de datos
        List<Producto> productos = productoRepository.findAll();

        // 2. Mapear los productos a DTOs
        return productos.stream()
                .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                .collect(Collectors.toList());
    }
}
