package com.petwellness.service.impl;

import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.entity.Archivos;
import com.petwellness.repository.MascotaDatosRepository;
import com.petwellness.service.ArchivoService;
import com.petwellness.service.MascotaDatosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminMascotaDatosServiceImpl implements MascotaDatosService {

    private final MascotaDatosRepository mascotaDatosRepository;
    private final ArchivoService archivoService;

    @Override
    public List<RegistroMascota> getAll() {
        return mascotaDatosRepository.findAll();
    }

    @Transactional
    @Override
    public RegistroMascota create(RegistroMascota registroMascota) {
        return mascotaDatosRepository.save(registroMascota);
    }

    @Override
    public RegistroMascota findById(int id) {
        return mascotaDatosRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public RegistroMascota update(Integer id, RegistroMascota registroMascota) {
        RegistroMascota existingMascota = findById(id);
        if (existingMascota == null) {
            throw new IllegalArgumentException("La mascota con ID " + id + " no existe.");
        }

        existingMascota.setNombre(registroMascota.getNombre());
        existingMascota.setEspecie(registroMascota.getEspecie());
        existingMascota.setGenero(registroMascota.getGenero());
        existingMascota.setRaza(registroMascota.getRaza());
        existingMascota.setEdad(registroMascota.getEdad());
        existingMascota.setFoto(registroMascota.getFoto());
        existingMascota.setFechaNacimiento(registroMascota.getFechaNacimiento());
        existingMascota.setDescripcion(registroMascota.getDescripcion());
        existingMascota.setDireccion(registroMascota.getDireccion());
        existingMascota.setMiembroID(registroMascota.getMiembroID());
        existingMascota.setTitularPoliza(registroMascota.getTitularPoliza());
        existingMascota.setInfoAdicional(registroMascota.getInfoAdicional());

        return mascotaDatosRepository.save(existingMascota);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        // Eliminar archivos asociados a la mascota
        deleteFilesAssociatedWithMascota(id);

        // Eliminar la mascota
        mascotaDatosRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteFilesAssociatedWithMascota(Integer id) {
        List<Archivos> archivos = archivoService.getAllByMascotaId(id);
        for (Archivos archivo : archivos) {
            archivoService.delete(archivo.getIdArchivos());
        }
    }
}
