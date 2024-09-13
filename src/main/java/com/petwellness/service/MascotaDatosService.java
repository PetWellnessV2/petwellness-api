package com.petwellness.service;

import com.petwellness.model.entity.RegistroMascota;

import java.util.List;

public interface MascotaDatosService {
    List<RegistroMascota> getAll();
    RegistroMascota findById(int id);
    RegistroMascota create(RegistroMascota registroMascota);
    RegistroMascota update(Integer id, RegistroMascota registroMascota);
    void delete(Integer id);
    void deleteFilesAssociatedWithMascota(Integer id);
}
