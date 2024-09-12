package com.petwellness.service.impl;

import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.MascotaDatosRepository;
import com.petwellness.service.MascotaDatosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminMascotaDatosServiceImpl implements MascotaDatosService {

    private final MascotaDatosRepository mascotaDatosRepository;

    @Override
    public List<RegistroMascota> getAll() {
        return mascotaDatosRepository.findAll();
    }

    @Transactional//(readOnly = true)
    @Override
    public Page<RegistroMascota> paginate(Pageable pageable) {
        return mascotaDatosRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public RegistroMascota create(RegistroMascota registroMascota) {
        return mascotaDatosRepository.save(registroMascota);
    }

    @Override
    public RegistroMascota findById(int id) {
        Optional<RegistroMascota> registroMascota = mascotaDatosRepository.findById(id);
        return registroMascota.orElse(null);
    }

    @Override
    @Transactional
    public RegistroMascota update(Integer id, RegistroMascota registroMascota) {
        // Buscar la mascota por ID
        RegistroMascota existingMascota = findById(id);

        // Validar si la mascota existe
        if (existingMascota == null) {
            throw new IllegalArgumentException("La mascota con ID " + id + " no existe.");
        }

        // Actualizar los datos de la mascota existente
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

        // Guardar los cambios en la base de datos
        return mascotaDatosRepository.save(existingMascota);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // Buscar la mascota por ID
        RegistroMascota existingMascota = findById(id);

        // Validar si la mascota existe
        if (existingMascota == null) {
            throw new IllegalArgumentException("La mascota con ID " + id + " no existe.");
        }

        // Eliminar la mascota
        mascotaDatosRepository.deleteById(id);
    }
}
