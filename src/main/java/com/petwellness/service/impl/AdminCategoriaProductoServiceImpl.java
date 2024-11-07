package com.petwellness.service.impl;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petwellness.service.AdminCategoriaProductoService;
import com.petwellness.repository.CategoriaProductoRepository;
import com.petwellness.dto.CategoriaProductoDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.CategoriaProductoMapper;
import com.petwellness.model.entity.CategoriaProducto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminCategoriaProductoServiceImpl implements AdminCategoriaProductoService {
    private final CategoriaProductoRepository categoriaProductoRepository;
    private final CategoriaProductoMapper categoriaProductoMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CategoriaProductoDTO> getAll() {
        List<CategoriaProducto> categoriaProductos = categoriaProductoRepository.findAll();
        return categoriaProductos.stream()
                .map(categoriaProductoMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CategoriaProductoDTO getById(Integer id) {
        CategoriaProducto categoriaProducto = categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de producto no encontrada"));
        return categoriaProductoMapper.toDTO(categoriaProducto);
    }

    @Transactional
    @Override
    public CategoriaProductoDTO create(CategoriaProductoDTO categoriaProductoDTO) {
        categoriaProductoRepository.findByName(categoriaProductoDTO.getName())
                .ifPresent(categoriaProducto -> {
                    throw new BadRequestException("Ya existe una categoría de producto con el nombre " + categoriaProductoDTO.getName());
                });
        CategoriaProducto categoriaProducto = categoriaProductoMapper.toEntity(categoriaProductoDTO);
        categoriaProducto.setCreatedAt(LocalDateTime.now());
        categoriaProducto = categoriaProductoRepository.save(categoriaProducto);
        return categoriaProductoMapper.toDTO(categoriaProducto);
    }
    @Override
    public CategoriaProductoDTO update(Integer id, CategoriaProductoDTO categoriaProductoDTO) {
        CategoriaProducto categoriaProducto = categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de producto no encontrada"));

        categoriaProductoRepository.findByName(categoriaProductoDTO.getName())
                .filter(existingCategoriaProducto -> !existingCategoriaProducto.getId().equals(id))
                .ifPresent(existingCategoriaProducto -> {
                    throw new BadRequestException("Ya existe una categoría de producto con el nombre " + categoriaProductoDTO.getName());
                });
        categoriaProducto.setName(categoriaProductoDTO.getName());
        categoriaProducto.setDescription(categoriaProductoDTO.getDescription());
        categoriaProducto.setUpdatedAt(LocalDateTime.now());

        categoriaProducto = categoriaProductoRepository.save(categoriaProducto);
        return categoriaProductoMapper.toDTO(categoriaProducto);
    }
    @Override
    public void delete(Integer id) {
        CategoriaProducto categoriaProducto = categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria de producto no encontrada"));
        categoriaProductoRepository.delete(categoriaProducto);
    }
}
