package com.petwellness.service.impl;

import com.petwellness.dto.ProductoDTO;
import com.petwellness.model.entity.Producto;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> getAllProductos() {
        return productoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> getAllProductosPaginated(Pageable pageable) {
        return productoRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO getProductoById(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToDTO(producto);
    }

    @Override
    @Transactional
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        Producto producto = mapToEntity(productoDTO);
        producto = productoRepository.save(producto);
        return mapToDTO(producto);
    }

    @Override
    @Transactional
    public ProductoDTO updateProducto(Integer id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(productoDTO.getNombre());
        producto.setImagen(productoDTO.getImagen());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setCosto(productoDTO.getCosto());
        producto.setCategoriaProducto(producto.getCategoriaProducto());
        producto.setStock(productoDTO.getStock());

        producto = productoRepository.save(producto);
        return mapToDTO(producto);
    }

    @Override
    @Transactional
    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    private ProductoDTO mapToDTO(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }

    private Producto mapToEntity(ProductoDTO productoDTO) {
        return modelMapper.map(productoDTO, Producto.class);
    }

    private void updateProductoFromDTO(Producto producto, ProductoDTO productoDTO) {
        modelMapper.map(productoDTO, producto);
    }
}