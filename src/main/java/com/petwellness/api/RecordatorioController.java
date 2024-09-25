package com.petwellness.api;

import com.petwellness.dto.RecordatorioDTO;
import com.petwellness.model.entity.Recordatorio;
import com.petwellness.service.RecordatorioService;
import com.petwellness.model.entity.Usuario;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.service.UsuarioService;
import com.petwellness.service.MascotaDatosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recordatorios")
public class RecordatorioController {

    private final RecordatorioService recordatorioService;
    private final UsuarioService usuarioService;
    private final MascotaDatosService mascotaDatosService;

    @GetMapping
    public ResponseEntity<List<RecordatorioDTO>> getAllRecordatorios() {
        List<Recordatorio> recordatorios = recordatorioService.getAll();
        List<RecordatorioDTO> dtos = recordatorios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordatorioDTO> getRecordatorioById(@PathVariable Integer id) {
        Recordatorio recordatorio = recordatorioService.findById(id);
        if (recordatorio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RecordatorioDTO dto = convertToDTO(recordatorio);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecordatorioDTO> createRecordatorio(@RequestBody RecordatorioDTO dto) {
        try {
            Recordatorio recordatorio = convertToEntity(dto);
            Recordatorio saved = recordatorioService.create(recordatorio);
            RecordatorioDTO savedDTO = convertToDTO(saved);
            return new ResponseEntity<>(savedDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecordatorioDTO> updateRecordatorio(
            @PathVariable Integer id, @RequestBody RecordatorioDTO dto) {
        try {
            Recordatorio recordatorio = convertToEntity(dto);
            Recordatorio updated = recordatorioService.update(id, recordatorio);
            RecordatorioDTO updatedDTO = convertToDTO(updated);
            return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecordatorio(@PathVariable Integer id) {
        try {
            recordatorioService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private RecordatorioDTO convertToDTO(Recordatorio recordatorio) {
        RecordatorioDTO dto = new RecordatorioDTO();
        dto.setIdRecordatorio(recordatorio.getIdRecordatorio());
        dto.setTipoRecordatorio(recordatorio.getTipoRecordatorio());
        dto.setFecha(recordatorio.getFecha());
        dto.setHora(recordatorio.getHora());
        dto.setDescripcion(recordatorio.getDescripcion());
        dto.setCompletado(recordatorio.getCompletado());
        dto.setMascotaId(recordatorio.getMascota() != null ? recordatorio.getMascota().getIdMascota() : null);
        dto.setUsuarioId(recordatorio.getUsuario() != null ? recordatorio.getUsuario().getUserId() : null);
        dto.setFechaHora(recordatorio.getFechaHora());
        dto.setTitulo(recordatorio.getTitulo());
        return dto;
    }

    private Recordatorio convertToEntity(RecordatorioDTO dto) {
        Recordatorio recordatorio = new Recordatorio();
        recordatorio.setTipoRecordatorio(dto.getTipoRecordatorio());
        recordatorio.setFecha(dto.getFecha());
        recordatorio.setHora(dto.getHora());
        recordatorio.setDescripcion(dto.getDescripcion());
        recordatorio.setCompletado(dto.getCompletado());
        recordatorio.setFechaHora(dto.getFechaHora());
        recordatorio.setTitulo(dto.getTitulo());
        if (dto.getMascotaId() != null) {
            RegistroMascota mascota = mascotaDatosService.findById(dto.getMascotaId());
            recordatorio.setMascota(mascota);
        }
        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioService.findById(dto.getUsuarioId());
            recordatorio.setUsuario(usuario);
        }
        return recordatorio;
    }

    @PostMapping("/generar-automaticos")
    public ResponseEntity<Void> generarRecordatoriosAutomaticos() {
        try {
            recordatorioService.generarRecordatoriosAutomaticos();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
