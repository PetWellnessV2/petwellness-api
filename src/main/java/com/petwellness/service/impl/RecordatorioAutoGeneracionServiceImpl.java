package com.petwellness.service.impl;

import com.petwellness.model.entity.EventoSalud;
import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.enums.RecordatorioStatus;
import com.petwellness.model.enums.TipoRecordatorio;
import com.petwellness.repository.EventoSaludRepository;
import com.petwellness.repository.RecordatorioRepository;
import com.petwellness.service.RecordatorioAutoGeneracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordatorioAutoGeneracionServiceImpl implements RecordatorioAutoGeneracionService {

    private final EventoSaludRepository eventoSaludRepository;
    private final RecordatorioRepository recordatorioRepository;

    // Ejecuta el metodo todos los días a la medianoche
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    @Override
    public void generarRecordatoriosAutomaticos() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = hoy.plusDays(7); // Configurable según tus necesidades

        List<EventoSalud> eventosProximos = eventoSaludRepository.findByFechaEventoBetween(hoy, fechaLimite);

        for (EventoSalud evento : eventosProximos) {
            boolean recordatorioExistente = recordatorioRepository.existsByUsuario_UserIdAndMascota_IdMascotaAndFechaHoraAndTipoRecordatorio(
                    evento.getMascota().getUsuario().getUserId(),
                    evento.getMascota().getIdMascota(),
                    evento.getFechaEvento().atStartOfDay(),
                    TipoRecordatorio.AUTOMATICO);

            if (!recordatorioExistente) {
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setUsuario(evento.getMascota().getUsuario());
                recordatorio.setMascota(evento.getMascota());
                recordatorio.setTitulo("Recordatorio de " + evento.getTipoEvento().name());
                recordatorio.setDescripcion(evento.getDescripcion());
                recordatorio.setFechaHora(evento.getFechaEvento().atStartOfDay());
                recordatorio.setTipoRecordatorio(TipoRecordatorio.AUTOMATICO);
                recordatorio.setRecordatorioStatus(RecordatorioStatus.CREADO);

                recordatorioRepository.save(recordatorio);
            }
        }
    }
}
