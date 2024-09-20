package com.petwellness.api;

import com.petwellness.model.entity.HorariosDisponibles;
import com.petwellness.service.HorariosDisponiblesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/horarios-disponibles")
public class HorariosDisponiblesController {
    private final HorariosDisponiblesService horariosDisponiblesService;

    @PostMapping
    public ResponseEntity<HorariosDisponibles> agregarHorario(@RequestBody HorariosDisponibles horario) {
        HorariosDisponibles nuevoHorario = horariosDisponiblesService.agregarHorario(horario);
        return new ResponseEntity<>(nuevoHorario, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Integer id) {
        horariosDisponiblesService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<HorariosDisponibles>> obtenerHorarios() {
        List<HorariosDisponibles> horarios = horariosDisponiblesService.obtenerHorarios();
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @GetMapping("/veterinario/{userId}")
    public ResponseEntity<List<HorariosDisponibles>> obtenerHorariosPorVeterinario(@PathVariable Integer userId) {
        List<HorariosDisponibles> horarios = horariosDisponiblesService.obtenerHorariosPorVeterinarioId(userId);
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

}
