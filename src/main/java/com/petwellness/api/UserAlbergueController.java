package com.petwellness.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.service.UserAlbergueService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/albergue")
public class UserAlbergueController {

    private final UserAlbergueService userAlbergueService;

    @GetMapping("/{usuarioUserId}/mascotas")
    public ResponseEntity<List<RegistroMascotaDTO>> getMascotas(
            @PathVariable Integer usuarioUserId) {
        List<RegistroMascotaDTO> mascotas = userAlbergueService.findRegistroMascotasByUserId(usuarioUserId);
        return ResponseEntity.ok(mascotas);
    }

    @GetMapping("{usuarioUserId}/mascotas/filters")
    public ResponseEntity<List<RegistroMascotaDTO>> findAllWithFilters(
            @PathVariable Integer usuarioUserId, 
            @RequestParam(required = false) String nombre, 
            @RequestParam(required = false) Especie especie, 
            @RequestParam(required = false) Genero genero) {

        List<RegistroMascotaDTO> mascotas = userAlbergueService.findAllWithFilters(usuarioUserId, nombre, especie, genero);
        return ResponseEntity.ok(mascotas);
    }
}
