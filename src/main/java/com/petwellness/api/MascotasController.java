package com.petwellness.api;

import com.petwellness.dto.MascotaProfileDTO;
import com.petwellness.dto.MascotaRegistroDTO;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.service.MascotasService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/registromascotas")
public class MascotasController {

    private final MascotasService mascotasService;
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
    @GetMapping("/{usuarioUserId}/mascotas")
    public ResponseEntity<List<MascotaProfileDTO>> getMascotas(@PathVariable Integer usuarioUserId) {
        List<MascotaProfileDTO> mascotas = mascotasService.findMascotasByUser_id(usuarioUserId);
        return ResponseEntity.ok(mascotas);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
    @GetMapping
    public ResponseEntity<List<MascotaProfileDTO>> getAllRegistroMascotas() {
        List<MascotaProfileDTO> mascotas = mascotasService.getAll();
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
    @GetMapping("page")
    public ResponseEntity<Page<MascotaProfileDTO>> paginate(@PageableDefault(size = 5) Pageable pageable) {
        Page<MascotaProfileDTO> page = mascotasService.paginate(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<MascotaProfileDTO> getRegistroMascotaById(@PathVariable int id) {
        MascotaProfileDTO mascota = mascotasService.findById(id);
        return new ResponseEntity<>(mascota, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @PostMapping
    public ResponseEntity<MascotaProfileDTO> create(@Valid @RequestBody MascotaRegistroDTO mascotaRegistroDTO) {
        MascotaProfileDTO newMascota = mascotasService.create(mascotaRegistroDTO);
        return new ResponseEntity<>(newMascota, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<MascotaProfileDTO> updateRegistroMascota(@PathVariable Integer id, @Valid @RequestBody MascotaRegistroDTO mascotaRegistroDTO) {
        MascotaProfileDTO updatedMascota = mascotasService.update(id, mascotaRegistroDTO);
        return new ResponseEntity<>(updatedMascota, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistroMascota(@PathVariable Integer id) {
        mascotasService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
    // Filtro de mascotas por nombre, especie y genero
    @GetMapping("/filter")
    public ResponseEntity<List<MascotaProfileDTO>> findWithFilters(@RequestParam(required = false) String nombre,
                                                                    @RequestParam(required = false) Especie especie,
                                                                    @RequestParam(required = false) Genero genero) {

        List<MascotaProfileDTO> mascotas = mascotasService.findWithFilters(nombre, especie, genero);
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }
}
