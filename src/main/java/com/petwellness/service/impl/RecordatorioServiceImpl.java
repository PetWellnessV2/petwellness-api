package com.petwellness.service.impl;

import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.Usuario;
import com.petwellness.repository.RecordatorioRepository;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.service.RecordatorioService;
import com.petwellness.service.ConsultaService;
import com.petwellness.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordatorioServiceImpl implements RecordatorioService {

    private final RecordatorioRepository recordatorioRepository;
    private final ConsultaService consultaService;
    private final UsuarioService usuarioService;

    @Override
    public List<Recordatorio> getAll() {
        return recordatorioRepository.findAll();
    }

    @Override
    public Recordatorio findById(Integer id) {
        return recordatorioRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Recordatorio create(Recordatorio recordatorio) {
        return recordatorioRepository.save(recordatorio);
    }

    @Transactional
    @Override
    public Recordatorio update(Integer id, Recordatorio recordatorio) {
        Recordatorio existing = findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Recordatorio no encontrado con ID: " + id);
        }

        existing.setIdRecordatorio(recordatorio.getIdRecordatorio());
        existing.setTipoRecordatorio(recordatorio.getTipoRecordatorio());
        existing.setFecha(recordatorio.getFecha());
        existing.setHora(recordatorio.getHora());
        existing.setDescripcion(recordatorio.getDescripcion());
        existing.setCompletado(recordatorio.getCompletado());
        existing.setMascota(recordatorio.getMascota());
        existing.setUsuario(recordatorio.getUsuario());
        existing.setFechaHora(recordatorio.getFechaHora());
        existing.setTitulo(recordatorio.getTitulo());

        return recordatorioRepository.save(existing);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        recordatorioRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void generarRecordatoriosAutomaticos() {
        List<String> tiposConsulta = Arrays.asList("VACUNACION", "CHEQUEO");
        List<Consulta> consultas = consultaService.getUpcomingConsultasByTipo(tiposConsulta);
        for (Consulta consulta : consultas) {
            if (consulta.getFechaHora() == null || consulta.getRegistroMascota() == null || consulta.getTipoConsulta() == null) {
                continue;
            }

            String nombreMascota = consulta.getRegistroMascota().getNombre();
            if (nombreMascota == null) {
                nombreMascota = "Mascota sin nombre";
            }

            Usuario usuario = consulta.getRegistroMascota().getUsuario();
            if (usuario == null) {
                continue;
            }

            String tipoConsultaNombre = consulta.getTipoConsulta().name();

            Recordatorio recordatorio = new Recordatorio();
            recordatorio.setIdRecordatorio(consulta.getIdConsulta());
            recordatorio.setTipoRecordatorio(tipoConsultaNombre);
            recordatorio.setFecha(consulta.getFechaHora().toLocalDate());
            recordatorio.setHora(consulta.getFechaHora().toLocalTime());
            recordatorio.setDescripcion("Recordatorio de " + tipoConsultaNombre +
                    " para la mascota: " + nombreMascota);
            recordatorio.setCompletado(false);
            recordatorio.setMascota(consulta.getRegistroMascota());
            recordatorio.setUsuario(usuario);
            recordatorio.setFechaHora(consulta.getFechaHora());
            recordatorio.setTitulo("Recordatorio de " + tipoConsultaNombre);

            try {
                recordatorioRepository.save(recordatorio);
            } catch (Exception e) {
                // Registra el error
                e.printStackTrace();
                // Opcional: manejar la excepción
            }
        }
    }


}
