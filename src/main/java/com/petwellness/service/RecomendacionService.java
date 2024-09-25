package com.petwellness.service;

import com.petwellness.model.entity.Producto;
import java.util.List;

public interface RecomendacionService {

    List<Producto> recomendarProductos(Integer idMascota);
}
