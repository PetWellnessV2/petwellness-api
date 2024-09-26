package com.petwellness.service;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MascotaDatosService {
    List<RegistroMascotaDTO> getAll();
    Page<RegistroMascotaDTO> paginate(Pageable pageable); // Corregido a la clase correcta de Spring
    RegistroMascotaDTO findById(int id);
    RegistroMascotaDTO create(RegistroMascotaDTO registroMascotaDTO);
    RegistroMascotaDTO update(Integer id, RegistroMascotaDTO registroMascota);
    void delete(Integer id);

    // Filtro de mascotas por nombre, especie y genero
    List<RegistroMascotaDTO> findWithFilters(String nombre, Especie especie, Genero genero);

}
