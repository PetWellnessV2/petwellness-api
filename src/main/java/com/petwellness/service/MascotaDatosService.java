package com.petwellness.service;

import com.petwellness.model.entity.RegistroMascota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MascotaDatosService {
    List<RegistroMascota> getAll();
    Page<RegistroMascota> paginate(Pageable pageable); // Corregido a la clase correcta de Spring
    RegistroMascota findById(int id);
    RegistroMascota create(RegistroMascota registroMascota);
    RegistroMascota update(Integer id, RegistroMascota registroMascota);
    void delete(Integer id);
}
