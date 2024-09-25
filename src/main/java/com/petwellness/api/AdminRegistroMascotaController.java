package com.petwellness.api;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.service.MascotaDatosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/registromascotas")
public class AdminRegistroMascotaController {

    private final MascotaDatosService mascotaDatosService;

    @GetMapping
    public ResponseEntity<List<RegistroMascotaDTO>> getAllRegistroMascotas() {
        List<RegistroMascotaDTO> mascotas = mascotaDatosService.getAll();
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }

    @GetMapping("page")
    public ResponseEntity<Page<RegistroMascotaDTO>> paginate(@PageableDefault(size = 5) Pageable pageable) {
        Page<RegistroMascotaDTO> page = mascotaDatosService.paginate(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RegistroMascotaDTO> getRegistroMascotaById(@PathVariable int id) {
        RegistroMascotaDTO mascota = mascotaDatosService.findById(id);
        return new ResponseEntity<>(mascota, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RegistroMascotaDTO> create(@Valid @RequestBody RegistroMascotaDTO registroMascotaDTO) {
        RegistroMascotaDTO newMascota = mascotaDatosService.create(registroMascotaDTO);
        return new ResponseEntity<>(newMascota, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroMascotaDTO> updateRegistroMascota(@PathVariable Integer id, @Valid @RequestBody RegistroMascotaDTO registroMascotaDTO) {
        RegistroMascotaDTO updatedMascota = mascotaDatosService.update(id, registroMascotaDTO);
        return new ResponseEntity<>(updatedMascota, HttpStatus.OK);
            
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistroMascota(@PathVariable Integer id) {
        mascotaDatosService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
