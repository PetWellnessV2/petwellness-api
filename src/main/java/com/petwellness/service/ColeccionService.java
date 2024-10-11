package com.petwellness.service;

import com.petwellness.dto.ColeccionDTO;
import java.util.List;

public interface ColeccionService {
    List<ColeccionDTO> obtenerColeccionesDeUsuario(Integer usuarioId);
    ColeccionDTO crearColeccion(ColeccionDTO coleccionDTO);
    ColeccionDTO actualizarColeccion(Integer id, ColeccionDTO coleccionDTO);
    void eliminarColeccion(Integer id);
    ColeccionDTO agregarProductoAColeccion(Integer coleccionId, Integer productoId);
    void eliminarProductoDeColeccion(Integer coleccionId, Integer productoId);
}