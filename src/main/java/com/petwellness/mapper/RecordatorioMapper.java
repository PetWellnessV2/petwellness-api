package com.petwellness.mapper;

import com.petwellness.dto.RecordatorioDTO;
import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.entity.Mascota;
import com.petwellness.repository.UserRepository;
import com.petwellness.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RecordatorioMapper {

    private final MascotaRepository mascotaDatosRepository;

    public RecordatorioDTO toDTO(Recordatorio recordatorio) {
        if (recordatorio == null) {
            return null;
        }

        RecordatorioDTO dto = new RecordatorioDTO();
        dto.setRecordatorioId(recordatorio.getRecordatorioId());
        dto.setUsuarioId(recordatorio.getUsuario().getUserId());
        dto.setMascotaId(recordatorio.getMascota().getIdMascota());
        dto.setTitulo(recordatorio.getTitulo());
        dto.setDescripcion(recordatorio.getDescripcion());
        dto.setTipoRecordatorio(recordatorio.getTipoRecordatorio());
        dto.setFechaHora(recordatorio.getFechaHora());
        dto.setRecordatorioStatus(recordatorio.getRecordatorioStatus());
        return dto;
    }

    public Recordatorio toEntity(RecordatorioDTO dto) {
        if (dto == null) {
            return null;
        }

        Recordatorio recordatorio = new Recordatorio();
        updateEntityFromDTO(recordatorio, dto);
        return recordatorio;
    }

    public void updateEntityFromDTO(Recordatorio recordatorio, RecordatorioDTO dto) {
        recordatorio.setTitulo(dto.getTitulo());
        recordatorio.setDescripcion(dto.getDescripcion());
        recordatorio.setTipoRecordatorio(dto.getTipoRecordatorio());
        recordatorio.setFechaHora(dto.getFechaHora());
        recordatorio.setRecordatorioStatus(dto.getRecordatorioStatus());
        /*
        Customer usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        recordatorio.setUsuario(usuario);
        */
        Mascota mascota = mascotaDatosRepository.findById(dto.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        recordatorio.setMascota(mascota);
    }
}