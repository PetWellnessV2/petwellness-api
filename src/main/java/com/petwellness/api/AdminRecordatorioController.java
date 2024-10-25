package com.petwellness.api;

import com.petwellness.service.AdminRecordatorioService;
import com.petwellness.service.RecordatorioAutoGeneracionService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/recordatorio")
@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
public class AdminRecordatorioController {
    private final AdminRecordatorioService adminRecordatorioService;
    private final RecordatorioAutoGeneracionService recordatorioAutoGeneracionService;
    /*
    @GetMapping
    public ResponseEntity<List<Recordatorio>> getAllRecordatorios() {
        return ResponseEntity.ok(adminRecordatorioService.getAll());
    }

    @GetMapping("/usuario/{usuarioId}/page")
    public ResponseEntity<Page<Recordatorio>> paginateRecordatoriosByUsuario(
            @PathVariable("usuarioId") Integer usuarioId,
            @PageableDefault(size = 5, sort = "fechaHora", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Recordatorio> recordatorios = adminRecordatorioService.paginateByUsuarioId(usuarioId, pageable);
        return new ResponseEntity<>(recordatorios, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Recordatorio>> getRecordatoriosByUsuario(@PathVariable("usuarioId") Integer usuarioId) {
        List<Recordatorio> recordatorios = adminRecordatorioService.findByUsuarioId(usuarioId);
        return new ResponseEntity<>(recordatorios, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}/enviados")
    public ResponseEntity<List<Recordatorio>> getSentRecordatorios(@PathVariable("usuarioId") Integer usuarioId) {
        List<Recordatorio> recordatorios = adminRecordatorioService.findByUsuarioIdAndStatus(usuarioId, RecordatorioStatus.ENVIADO);
        return new ResponseEntity<>(recordatorios, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}/no-enviados")
    public ResponseEntity<List<Recordatorio>> getUnsentRecordatorios(@PathVariable("usuarioId") Integer usuarioId) {
        List<Recordatorio> recordatorios = adminRecordatorioService.findByUsuarioIdAndStatus(usuarioId, RecordatorioStatus.CREADO);
        return new ResponseEntity<>(recordatorios, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecordatorioDTO> createRecordatorio(@RequestBody RecordatorioDTO recordatorioDTO) {
        RecordatorioDTO nuevoRecordatorio = adminRecordatorioService.createRecordatorio(recordatorioDTO);
        return new ResponseEntity<>(nuevoRecordatorio, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecordatorioDTO> updateRecordatorio(@PathVariable Integer id, @RequestBody RecordatorioDTO recordatorioDTO) {
        RecordatorioDTO recordatorioActualizado = adminRecordatorioService.updateRecordatorio(id, recordatorioDTO);
        return new ResponseEntity<>(recordatorioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/usuario/{usuarioId}/recordatorio/{recordatorioId}")
    public ResponseEntity<Void> deleteRecordatorioByIdAndUsuario(
            @PathVariable Integer usuarioId,
            @PathVariable Integer recordatorioId) {
        adminRecordatorioService.deleteRecordatorioByIdAndUsuarioId(recordatorioId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recordatorioId}")
    public ResponseEntity<Void> deleteRecordatorio(@PathVariable Integer recordatorioId) {
        adminRecordatorioService.deleteRecordatorioById(recordatorioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> deleteRecordatoriosByUsuario(@PathVariable Integer usuarioId) {
        adminRecordatorioService.deleteRecordatoriosByUsuarioId(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordatorioDTO> getRecordatorioById(@PathVariable Integer id) {
        RecordatorioDTO recordatorio = adminRecordatorioService.getRecordatorioById(id);
        return ResponseEntity.ok(recordatorio);
    }

    @PostMapping("/generar-automaticos")
    public ResponseEntity<String> generarRecordatoriosAutomaticos() {
        recordatorioAutoGeneracionService.generarRecordatoriosAutomaticos();
        return ResponseEntity.ok("Recordatorios generados correctamente.");
    }

     */
}