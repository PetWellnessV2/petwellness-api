package com.petwellness.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petwellness.dto.RegistroMascotaDTO;

import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.ExamenesLaboratorio;
import com.petwellness.service.UserVeterinarioService;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/veterinario")
@PreAuthorize("hasRole('VETERINARIO')")
public class UserVeterinarioController {
    private final UserVeterinarioService veterinarioService;

    // Listar mascotas asignadas a un veterinario
    @GetMapping("/{usuarioUserId}/mascotas")
    public ResponseEntity<List<RegistroMascotaDTO>> getMascotas(@PathVariable Integer usuarioUserId) {
        List<RegistroMascotaDTO> mascotas = veterinarioService.findRegistroMascotasByUserId(usuarioUserId);
        return ResponseEntity.ok(mascotas);
    }

    // Listar consultas de una mascota asignada a un veterinario
    @GetMapping("/{usuarioUserId}/mascotas/{mascotaId}/consultas")
    public ResponseEntity<List<Consulta>> getConsultas(
            @PathVariable Integer mascotaId, @PathVariable Integer usuarioUserId) {
        List<Consulta> consultas = veterinarioService.findConsultasByMascotaAndUserId(mascotaId, usuarioUserId);
        return ResponseEntity.ok(consultas);
    }

    // Listar exámenes físicos de una mascota asignada a un veterinario
    @GetMapping("/{usuarioUserId}/mascotas/{mascotaId}/examenes-fisicos")
    public ResponseEntity<List<ExamenFisico>> getExamenesFisicos(
            @PathVariable Integer mascotaId, @PathVariable Integer usuarioUserId) {
        List<ExamenFisico> examenesFisicos = veterinarioService.findExamenesFisicosByMascotaAndUserId(mascotaId,
                usuarioUserId);
        return ResponseEntity.ok(examenesFisicos);
    }

    // Listar exámenes de laboratorio de una mascota asignada a un veterinario
    @GetMapping("/{usuarioUserId}/mascotas/{mascotaId}/examenes-laboratorio")
    public ResponseEntity<List<ExamenesLaboratorio>> getExamenesLaboratorio(
            @PathVariable Integer mascotaId, @PathVariable Integer usuarioUserId) {
        List<ExamenesLaboratorio> examenesLaboratorio = veterinarioService
                .findExamenesLaboratorioByMascotaAndUserId(mascotaId, usuarioUserId);
        return ResponseEntity.ok(examenesLaboratorio);
    }

    // Posponer consulta
    @PutMapping("/consultas/{consultaId}")
    public ResponseEntity<Consulta> posponerConsulta(
            @PathVariable Integer consultaId,
            @RequestParam Integer nuevaHora,
            @RequestParam String nuevaFecha,
            @RequestParam Integer veterinarioUserId) {
            
        Consulta consulta = veterinarioService.posponerConsulta(consultaId, nuevaHora, nuevaFecha, veterinarioUserId);
        return ResponseEntity.ok(consulta);
    }
}