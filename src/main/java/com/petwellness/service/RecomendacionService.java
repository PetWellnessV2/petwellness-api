package com.petwellness.service;

import com.petwellness.dto.ProductoDTO;

import java.util.List;

public interface RecomendacionService {
    List<ProductoDTO> getRecomendaciones(Integer mascotaId);

    <Producto> List<Producto> getRecomendaciones(Long mascotaId);
}
