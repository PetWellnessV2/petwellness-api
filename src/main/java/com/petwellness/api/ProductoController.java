package com.petwellness.api;

import com.petwellness.dto.ProductoDTO;
import com.petwellness.model.entity.Producto;
import com.petwellness.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        List<ProductoDTO> dtos = productos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Integer idProducto) {
        Producto producto = productoService.getProductoById(idProducto);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProductoDTO dto = convertToDTO(producto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@Valid @RequestBody ProductoDTO dto) {
        Producto producto = convertToEntity(dto);
        Producto createdProducto = productoService.createProducto(producto);
        ProductoDTO createdDTO = convertToDTO(createdProducto);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Integer idProducto, @Valid @RequestBody ProductoDTO dto) {
        try {
            Producto producto = convertToEntity(dto);
            Producto updatedProducto = productoService.updateProducto(idProducto, producto);
            ProductoDTO updatedDTO = convertToDTO(updatedProducto);
            return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer idProducto) {
        try {
            productoService.deleteProducto(idProducto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
        return dto;
    }

    private Producto convertToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setIdProducto(dto.getIdProducto());
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setImagen(dto.getImagen());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCosto(dto.getCosto());
        producto.setTipoProducto(dto.getTipoProducto());
        producto.setStock(dto.getStock());
        return producto;
    }
}
