package com.petwellness.service;

import java.util.List;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;

public interface UserAlbergueService {

    List<RegistroMascotaDTO> findRegistroMascotasByUserId(Integer usuarioId);

    List<RegistroMascotaDTO> findAllWithFilters(Integer usuarioId, String nombre, Especie especie, Genero genero);

}
