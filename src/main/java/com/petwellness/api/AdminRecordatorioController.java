package com.petwellness.api;

import com.petwellness.model.entity.Recordatorio;
import com.petwellness.service.AdminRecordatorioService;
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
@RequestMapping("/admin/recordatorio")
public class AdminRecordatorioController {
    private final AdminRecordatorioService adminRecordatorioService;

    @GetMapping
    public ResponseEntity<List<Recordatorio>> getAllRecordatorios() {
        return ResponseEntity.ok(adminRecordatorioService.getAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Recordatorio>> paginateCategories(
            @PageableDefault(size = 5, sort = "name") Pageable pageable) {
        Page<Recordatorio> recordatorios = adminRecordatorioService.paginate(pageable);
        return new ResponseEntity<Page<Recordatorio>>(recordatorios, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Recordatorio> getRecordatorioById(@PathVariable("id") Integer id) {
        Recordatorio recordatorio = adminRecordatorioService.findById(id);
        return new ResponseEntity<Recordatorio>(recordatorio, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Recordatorio> createRecordatorio(Recordatorio recordatorio) {
        Recordatorio newRecordatorio = adminRecordatorioService.create(recordatorio);
        return new ResponseEntity<Recordatorio>(newRecordatorio, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Recordatorio> updateRecordatorio(@PathVariable("id") Integer id,
                                                           @RequestBody Recordatorio recordatorio) {
        Recordatorio updateRecordatorio = adminRecordatorioService.update(id, recordatorio);
        return new ResponseEntity<Recordatorio>(updateRecordatorio, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recordatorio> deleteRecordatorio(@PathVariable("id") Integer id) {
        adminRecordatorioService.delete(id);
        return new ResponseEntity<Recordatorio>(HttpStatus.NO_CONTENT);
    }

}