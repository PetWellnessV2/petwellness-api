package com.petwellness.service.impl;

import com.petwellness.dto.ArchivoDTO;
import com.petwellness.model.entity.Archivos;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.ArchivoRepository;
import com.petwellness.service.ArchivoService;
import com.petwellness.service.MascotaDatosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;
    private final MascotaDatosService mascotaDatosService;

    // Mapper de Entidad a DTO
    private ArchivoDTO mapToDTO(Archivos archivo) {
        ArchivoDTO dto = new ArchivoDTO();
        dto.setId(archivo.getIdArchivos());
        dto.setIdMascota(archivo.getRegistroMascota().getIdMascota());
        dto.setTitulo(archivo.getNombreArchivo());
        dto.setDescripcion(archivo.getDescripcionArchivo());
        dto.setFechaHora(archivo.getFecha());
        return dto;
    }

    // Mapper de DTO a Entidad
    private Archivos mapToEntity(ArchivoDTO archivoDTO) {
        Archivos archivo = new Archivos();
        archivo.setNombreArchivo(archivoDTO.getTitulo());
        archivo.setDescripcionArchivo(archivoDTO.getDescripcion());
        archivo.setFecha(archivoDTO.getFechaHora());

        // Buscar y asignar la entidad RegistroMascota
        RegistroMascota registroMascota = mascotaDatosService.findById(archivoDTO.getIdMascota());
        if (registroMascota == null) {
            throw new RuntimeException("La mascota con ID " + archivoDTO.getIdMascota() + " no existe.");
        }
        archivo.setRegistroMascota(registroMascota);

        return archivo;
    }

    @Transactional
    @Override
    public ArchivoDTO createArchivo(ArchivoDTO archivoDTO) {
        Archivos archivo = mapToEntity(archivoDTO);
        archivoRepository.save(archivo);
        return mapToDTO(archivo);
    }

    @Transactional
    @Override
    public ArchivoDTO updateArchivo(Integer id, ArchivoDTO archivoDTO) {
        Optional<Archivos> optionalArchivo = archivoRepository.findById(id);
        if (optionalArchivo.isEmpty()) {
            throw new RuntimeException("Archivo no encontrado");
        }
        Archivos archivo = optionalArchivo.get();
        archivo.setNombreArchivo(archivoDTO.getTitulo());
        archivo.setDescripcionArchivo(archivoDTO.getDescripcion());
        archivo.setFecha(archivoDTO.getFechaHora());

        // Reasignar la mascota asociada si es necesario
        RegistroMascota registroMascota = mascotaDatosService.findById(archivoDTO.getIdMascota());
        if (registroMascota == null) {
            throw new RuntimeException("La mascota con ID " + archivoDTO.getIdMascota() + " no existe.");
        }
        archivo.setRegistroMascota(registroMascota);

        archivoRepository.save(archivo);
        return mapToDTO(archivo);
    }

    @Transactional
    @Override
    public void deleteArchivo(Integer id) {
        archivoRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ArchivoDTO getArchivoById(Integer id) {
        Archivos archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Archivo no encontrado"));
        return mapToDTO(archivo);
    }

    @Transactional
    @Override
    public List<ArchivoDTO> getAllArchivos() {
        return archivoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
