package com.petwellness.service;

import com.petwellness.dto.MascotaProfileDTO;
import com.petwellness.dto.MascotaRegistroDTO;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MascotasService {
    List<MascotaProfileDTO> getAll();
    Page<MascotaProfileDTO> paginate(Pageable pageable); // Corregido a la clase correcta de Spring
    MascotaProfileDTO findById(int id);
    MascotaProfileDTO create(MascotaRegistroDTO mascotaRegistroDTO);
    MascotaProfileDTO update(Integer id, MascotaRegistroDTO registroMascota);
    void delete(Integer id);
    List<MascotaProfileDTO> findMascotasByUser_id(Integer usuario_user_id);
    // Filtro de mascotas por nombre, especie y genero
    List<MascotaProfileDTO> findWithFilters(String nombre, Especie especie, Genero genero);

}
