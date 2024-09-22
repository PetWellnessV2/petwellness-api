package com.petwellness.api;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.entity.Usuario;
import com.petwellness.service.MascotaDatosService;
import com.petwellness.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/registromascotas")
public class AdminRegistroMascotaController {

    private final MascotaDatosService mascotaDatosService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<RegistroMascotaDTO>> getAllRegistroMascotas() {
        List<RegistroMascota> registroMascotas = mascotaDatosService.getAll();
        List<RegistroMascotaDTO> registroMascotasDTO = registroMascotas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(registroMascotasDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroMascotaDTO> getRegistroMascotaById(@PathVariable int id) {
        try {
            RegistroMascota registroMascota = mascotaDatosService.findById(id);
            if (registroMascota == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Mascota no encontrada
            }
            RegistroMascotaDTO dto = convertToDTO(registroMascota);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Error: Mascota no existe
        }
    }

    private RegistroMascotaDTO convertToDTO(RegistroMascota registroMascota) {
        RegistroMascotaDTO dto = new RegistroMascotaDTO();
        dto.setIdMascota(registroMascota.getIdMascota());
        dto.setUsuarioId(registroMascota.getUsuario().getUserId());
        dto.setEspecie(registroMascota.getEspecie());
        dto.setGenero(registroMascota.getGenero());
        dto.setRaza(registroMascota.getRaza());
        dto.setNombre(registroMascota.getNombre());
        dto.setEdad(registroMascota.getEdad());
        dto.setFoto(registroMascota.getFoto());
        dto.setFechaNacimiento(registroMascota.getFechaNacimiento());
        dto.setDescripcion(registroMascota.getDescripcion());
        dto.setDireccion(registroMascota.getDireccion());
        dto.setMiembroID(registroMascota.getMiembroID());
        dto.setTitularPoliza(registroMascota.getTitularPoliza());
        dto.setInfoAdicional(registroMascota.getInfoAdicional());
        return dto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroMascotaDTO> updateRegistroMascota(
            @PathVariable Integer id,
            @RequestBody RegistroMascotaDTO registroMascotaDTO) {
        try {
            // Buscar la mascota en la base de datos
            RegistroMascota existingMascota = mascotaDatosService.findById(id);
            if (existingMascota == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Mascota no encontrada
            }

            // Actualizar los datos de la mascota
            existingMascota.setNombre(registroMascotaDTO.getNombre());
            existingMascota.setEspecie(registroMascotaDTO.getEspecie());
            existingMascota.setGenero(registroMascotaDTO.getGenero());
            existingMascota.setRaza(registroMascotaDTO.getRaza());
            existingMascota.setEdad(registroMascotaDTO.getEdad());
            existingMascota.setFoto(registroMascotaDTO.getFoto());
            existingMascota.setFechaNacimiento(registroMascotaDTO.getFechaNacimiento());
            existingMascota.setDescripcion(registroMascotaDTO.getDescripcion());
            existingMascota.setDireccion(registroMascotaDTO.getDireccion());
            existingMascota.setMiembroID(registroMascotaDTO.getMiembroID());
            existingMascota.setTitularPoliza(registroMascotaDTO.getTitularPoliza());
            existingMascota.setInfoAdicional(registroMascotaDTO.getInfoAdicional());

            // Guardar los cambios
            RegistroMascota updatedMascota = mascotaDatosService.update(id, existingMascota);
            RegistroMascotaDTO updatedDTO = convertToDTO(updatedMascota);

            return new ResponseEntity<>(updatedDTO, HttpStatus.OK); // Mascota actualizada correctamente
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Error: datos inválidos
        }
    }

    private RegistroMascota convertToEntity(RegistroMascotaDTO dto) {
        RegistroMascota registroMascota = new RegistroMascota();
        registroMascota.setNombre(dto.getNombre());
        registroMascota.setEspecie(dto.getEspecie());
        registroMascota.setGenero(dto.getGenero());
        registroMascota.setRaza(dto.getRaza());
        registroMascota.setEdad(dto.getEdad());
        registroMascota.setFoto(dto.getFoto());
        registroMascota.setFechaNacimiento(dto.getFechaNacimiento());
        registroMascota.setDescripcion(dto.getDescripcion());
        registroMascota.setDireccion(dto.getDireccion());
        registroMascota.setMiembroID(dto.getMiembroID());
        registroMascota.setTitularPoliza(dto.getTitularPoliza());
        registroMascota.setInfoAdicional(dto.getInfoAdicional());
        registroMascota.setUsuario(usuarioService.getUsuarioById(dto.getUsuarioId()).get());
        return registroMascota;
    }

    @PostMapping
    public ResponseEntity<RegistroMascotaDTO> createRegistroMascota(
            @RequestBody RegistroMascotaDTO registroMascotaDTO) {
        try {
            RegistroMascota registroMascota = convertToEntity(registroMascotaDTO);

            // Buscar el usuario por ID y asociarlo a la mascota
            Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(registroMascotaDTO.getUsuarioId());
            if (usuarioOptional.isPresent()) {
                registroMascota.setUsuario(usuarioOptional.get());
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuario no encontrado
            }

            RegistroMascota nuevaMascota = mascotaDatosService.create(registroMascota);
            RegistroMascotaDTO nuevaMascotaDTO = convertToDTO(nuevaMascota);
            return new ResponseEntity<>(nuevaMascotaDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Error al crear la mascota
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistroMascota(@PathVariable Integer id) {
        try {
            mascotaDatosService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Mascota eliminada correctamente
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Mascota no encontrada
        }
    }
}
