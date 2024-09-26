package com.petwellness.service.impl;

import com.petwellness.dto.ArchivoDTO;
import com.petwellness.dto.ArchivoRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.ArchivoRegistroMapper;
import com.petwellness.model.entity.Archivos;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.ArchivoRepository;
import com.petwellness.repository.MascotaDatosRepository;
import com.petwellness.service.ArchivoService;
import com.petwellness.service.MascotaDatosService;
import com.petwellness.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;
    private final MascotaDatosRepository mascotaDatosRepository;
    private final ArchivoRegistroMapper archivoRegistroMapper;
    private final StorageService storageService;

    @Transactional
    @Override
    public ArchivoRegistroDTO createArchivo(ArchivoRegistroDTO archivoRegistroDTO, String path) {
        System.out.println("ID Mascota: " + archivoRegistroDTO.getIdRegistroMascota());
        archivoRepository.findByNombreArchivo(archivoRegistroDTO.getNombreArchivo())
                .ifPresent(existingArchivo ->{
                    throw new BadRequestException("Ya existe un archivo con el mismo título");
                });
        Integer idMascota = archivoRegistroDTO.getIdRegistroMascota();
        RegistroMascota registroMascota = mascotaDatosRepository.findById(idMascota)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID "+idMascota+" no existe"));

        Archivos archivo = archivoRegistroMapper.toEntity(archivoRegistroDTO);
        archivo.setFecha(LocalDate.now());
        archivo.setPath(path);
        archivo.setRegistroMascota(registroMascota);
        archivo = archivoRepository.save(archivo);
        return archivoRegistroMapper.toDTO(archivo);
    }

    @Transactional
    @Override
    public ArchivoRegistroDTO updateArchivo(Integer id, ArchivoRegistroDTO archivoRegistroDTO) {
        Archivos archivosFromDB = archivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El archivo con ID "+id+" no existe"));
        archivoRepository.findByNombreArchivo(archivoRegistroDTO.getNombreArchivo())
                .filter(existingArchivo -> !existingArchivo.getIdArchivos().equals(id))
                .ifPresent(existingArchivo ->{
                    throw new BadRequestException("Ya existe un archivo con el mismo título");
                });

        Integer idMascota = archivoRegistroDTO.getIdRegistroMascota();
        RegistroMascota registroMascota = mascotaDatosRepository.findById(idMascota)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID "+idMascota+" no existe"));

        archivosFromDB.setNombreArchivo(archivoRegistroDTO.getNombreArchivo());
        archivosFromDB.setDescripcionArchivo(archivoRegistroDTO.getDescripcion());
        archivosFromDB.setFecha(LocalDate.now());
        archivosFromDB.setPath(archivoRegistroDTO.getPath());
        archivosFromDB.setRegistroMascota(registroMascota);
        archivosFromDB = archivoRepository.save(archivosFromDB);
        return archivoRegistroMapper.toDTO(archivosFromDB);
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
    public ArchivoDTO getArchivoById(Integer id) {
        Archivos archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El archivo con ID "+id+" no existe"));
        ArchivoDTO archivoDTO = new ArchivoDTO();
        archivoDTO.setId(archivo.getIdArchivos());
        archivoDTO.setNombreArchivo(archivo.getNombreArchivo());
        archivoDTO.setDescripcion(archivo.getDescripcionArchivo());
        archivoDTO.setPath(archivo.getPath());
        archivoDTO.setNomMascota(archivo.getRegistroMascota().getNombre());
        return archivoDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ArchivoDTO> getAllArchivos() {
        List<Archivos> archivos = archivoRepository.findAll();
        return archivos.stream().map(archivo -> {
            ArchivoDTO archivoDTO = new ArchivoDTO();
            archivoDTO.setId(archivo.getIdArchivos());
            archivoDTO.setNombreArchivo(archivo.getNombreArchivo());
            archivoDTO.setDescripcion(archivo.getDescripcionArchivo());
            archivoDTO.setNomMascota(archivo.getRegistroMascota().getNombre());
            archivoDTO.setPath(archivo.getPath());
            return archivoDTO;
        }).toList();
    }
}
