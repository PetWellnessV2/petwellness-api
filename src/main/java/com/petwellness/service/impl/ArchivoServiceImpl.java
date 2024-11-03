package com.petwellness.service.impl;

import com.petwellness.dto.ArchivoProfileDTO;
import com.petwellness.dto.ArchivoRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.ArchivoMapper;
import com.petwellness.model.entity.Archivos;
import com.petwellness.model.entity.Mascota;
import com.petwellness.repository.ArchivoRepository;
import com.petwellness.repository.MascotaRepository;
import com.petwellness.service.ArchivoService;
import com.petwellness.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;
    private final MascotaRepository mascotaRepository;
    private final ArchivoMapper archivoMapper;
    private final StorageService storageService;

    @Transactional
    @Override
    public ArchivoProfileDTO createArchivo(ArchivoRegistroDTO archivoRegistroDTO, String path) {
        archivoRepository.findByNombre(archivoRegistroDTO.getNombre())
                .ifPresent(existingArchivo ->{
                    throw new BadRequestException("Ya existe un archivo con el mismo título");
                });
        Integer idMascota = archivoRegistroDTO.getIdMascota();
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID "+idMascota+" no existe"));

        Archivos archivo = archivoMapper.toEntity(archivoRegistroDTO);
        archivo.setFecha(LocalDate.now());
        archivo.setPath(path);
        archivo.setMascota(mascota);
        archivo = archivoRepository.save(archivo);
        return archivoMapper.toArchivoProfileDTO(archivo);
    }

    @Transactional
    @Override
    public ArchivoProfileDTO updateArchivo(Integer id, ArchivoRegistroDTO archivoRegistroDTO) {
        Archivos archivosFromDB = archivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El archivo con ID "+id+" no existe"));
        archivoRepository.findByNombre(archivoRegistroDTO.getNombre())
                .filter(existingArchivo -> !existingArchivo.getIdArchivos().equals(id))
                .ifPresent(existingArchivo ->{
                    throw new BadRequestException("Ya existe un archivo con el mismo título");
                });

        Integer idMascota = archivoRegistroDTO.getIdMascota();
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID "+idMascota+" no existe"));

        archivosFromDB.setNombre(archivoRegistroDTO.getNombre());
        archivosFromDB.setDescripcion(archivoRegistroDTO.getDescripcion());
        archivosFromDB.setFecha(LocalDate.now());
        archivosFromDB.setPath(archivoRegistroDTO.getPath());
        archivosFromDB.setMascota(mascota);
        archivosFromDB = archivoRepository.save(archivosFromDB);
        return archivoMapper.toArchivoProfileDTO(archivosFromDB);
    }

    @Transactional
    @Override
    public void deleteArchivo(Integer id) {
        Archivos archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El archivo con ID "+id+" no existe"));
        archivoRepository.delete(archivo);
    }

    @Transactional(readOnly = true)
    @Override
    public ArchivoProfileDTO getArchivoById(Integer id) {
        Archivos archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El archivo con ID "+id+" no existe"));
        ArchivoProfileDTO archivoDTO = new ArchivoProfileDTO();
        archivoDTO.setId(archivo.getIdArchivos());
        archivoDTO.setNombre(archivo.getNombre());
        archivoDTO.setDescripcion(archivo.getDescripcion());
        archivoDTO.setPath(archivo.getPath());
        archivoDTO.setNomMascota(archivo.getMascota().getNombre());
        return archivoDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ArchivoProfileDTO> getAllArchivos() {
        List<Archivos> archivos = archivoRepository.findAll();
        return archivos.stream().map(archivo -> {
            ArchivoProfileDTO archivoDTO = new ArchivoProfileDTO();
            archivoDTO.setId(archivo.getIdArchivos());
            archivoDTO.setNombre(archivo.getNombre());
            archivoDTO.setDescripcion(archivo.getDescripcion());
            archivoDTO.setNomMascota(archivo.getMascota().getNombre());
            archivoDTO.setPath(archivo.getPath());
            return archivoDTO;
        }).toList();
    }
}
