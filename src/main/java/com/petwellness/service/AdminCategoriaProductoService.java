package com.petwellness.service;


import com.petwellness.dto.CategoriaProductoDTO;
import java.util.List;

public interface AdminCategoriaProductoService {
    List<CategoriaProductoDTO> getAll();
    CategoriaProductoDTO getById(Integer id);
    CategoriaProductoDTO create(CategoriaProductoDTO categoriaProductoDTO);
    CategoriaProductoDTO update(Integer id, CategoriaProductoDTO categoriaProductoDTO);
    void delete(Integer id);
}
