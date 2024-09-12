package com.petwellness.api;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.service.MascotaDatosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/registromascotas")
public class AdminRegistroMascotaController {

    private final MascotaDatosService mascotaDatosService;

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
}
