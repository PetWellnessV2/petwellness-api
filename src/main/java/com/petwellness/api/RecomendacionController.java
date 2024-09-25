package com.petwellness.api;

import com.petwellness.dto.ProductoDTO;
import com.petwellness.model.entity.Producto;
import com.petwellness.service.RecomendacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recomendaciones")
public class RecomendacionController {

    private final RecomendacionService recomendacionService;

    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<ProductoDTO>> recomendarProductos(@PathVariable Integer idMascota) {
        List<Producto> productos = recomendacionService.recomendarProductos(idMascota);
        List<ProductoDTO> dtos = productos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    private ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setImagen(producto.getImagen());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCosto(producto.getCosto());
        dto.setTipoProducto(producto.getTipoProducto());
        dto.setStock(producto.getStock());
        // Agrega otros campos si es necesario
        return dto;
    }
}
