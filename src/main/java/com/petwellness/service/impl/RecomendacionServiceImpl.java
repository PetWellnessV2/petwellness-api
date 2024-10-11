package com.petwellness.service.impl;

import com.petwellness.dto.ProductoDTO;
import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.model.entity.Producto;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.TipoProducto;
import com.petwellness.repository.MascotaDatosRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.RecomendacionService;
import com.petwellness.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecomendacionServiceImpl implements RecomendacionService {

    private final MascotaDatosRepository mascotaRepository;
    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductoDTO> getRecomendaciones(Integer mascotaId) {
        // 1. Obtener la mascota por su ID
        RegistroMascotaDTO mascotaDTO = getMascotaById(mascotaId);

        // 2. Obtener la especie de la mascota
        Especie especie = mascotaDTO.getEspecie();

        // 3. Obtener los tipos de producto recomendados para la especie
        List<TipoProducto> tiposProducto = getTipoProductosPorEspecie(especie);

        // 4. Obtener los productos basados en los tipos de producto
        List<Producto> productos = productoRepository.findByTipoProductoIn(tiposProducto);

        // 5. Si no hay productos, obtener productos generales
        if (productos.isEmpty()) {
            productos = productoRepository.findAll(); // O puedes filtrar productos generales si lo prefieres
        }

        // 6. Mapear productos a DTOs
        List<ProductoDTO> productosDTO = productos.stream()
                .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                .collect(Collectors.toList());

        return productosDTO;
    }

    private RegistroMascotaDTO getMascotaById(Integer mascotaId) {
        return mascotaRepository.findById(mascotaId)
                .map(mascota -> modelMapper.map(mascota, RegistroMascotaDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID " + mascotaId + " no existe."));
    }

    private List<TipoProducto> getTipoProductosPorEspecie(Especie especie) {
        List<TipoProducto> tipos = new ArrayList<>();
        switch (especie) {
            case PERRO:
                tipos.add(TipoProducto.ALIMENTO);
                tipos.add(TipoProducto.JUGUETE);
                // Agrega más tipos si es necesario
                break;
            case GATO:
                tipos.add(TipoProducto.ALIMENTO);
                tipos.add(TipoProducto.JUGUETE);
                // Agrega más tipos si es necesario
                break;
            // Añade más casos para otras especies
            default:
                // Tipos de producto generales o vacíos
                break;
        }
        return tipos;
    }

    @Override
    public <Producto> List<Producto> getRecomendaciones(Long mascotaId) {
        return List.of();
    }
}
