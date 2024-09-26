package com.petwellness.service.impl;

import com.petwellness.dto.RecordatorioDTO;
import com.petwellness.model.entity.Recordatorio;
import com.petwellness.model.entity.Usuario;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.enums.RecordatorioStatus;
import com.petwellness.repository.RecordatorioRepository;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.repository.MascotaDatosRepository;
import com.petwellness.service.AdminRecordatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminRecordatorioServiceImpl implements AdminRecordatorioService {

    private final RecordatorioRepository recordatorioRepository;
    private final UsuarioRepository usuarioRepository;
    private final MascotaDatosRepository mascotaDatosRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Recordatorio> getAll() {
        return recordatorioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recordatorio> findByUsuarioId(Integer userId) {
        return recordatorioRepository.findByUsuario_UserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recordatorio> findByUsuarioIdAndStatus(Integer userId, RecordatorioStatus status) {
        return recordatorioRepository.findByUsuario_UserIdAndRecordatorioStatus(userId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recordatorio> paginateByUsuarioId(Integer usuarioId, Pageable pageable) {
        return recordatorioRepository.findByUsuario_UserId(usuarioId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recordatorio> paginate(Pageable pageable) {
        return recordatorioRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public RecordatorioDTO getRecordatorioById(Integer id) {
        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));
        return mapToDTO(recordatorio);
    }

    @Override
    @Transactional
    public RecordatorioDTO createRecordatorio(RecordatorioDTO recordatorioDTO) {
        Recordatorio recordatorio = mapToEntity(recordatorioDTO);
        recordatorio.setFechaHora(LocalDateTime.now());
        recordatorio.setRecordatorioStatus(RecordatorioStatus.CREADO);
        recordatorio = recordatorioRepository.save(recordatorio);
        return mapToDTO(recordatorio);
    }

    @Override
    @Transactional
    public RecordatorioDTO updateRecordatorio(Integer id, RecordatorioDTO recordatorioDTO) {
        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));
        updateEntityFromDTO(recordatorio, recordatorioDTO);
        recordatorio = recordatorioRepository.save(recordatorio);
        return mapToDTO(recordatorio);
    }

    @Override
    @Transactional
    public void deleteRecordatoriosByUsuarioId(Integer usuarioId) {
        List<Recordatorio> recordatorios = recordatorioRepository.findByUsuario_UserId(usuarioId);
        recordatorioRepository.deleteAll(recordatorios);
    }

    private Recordatorio mapToEntity(RecordatorioDTO dto) {
        Recordatorio recordatorio = new Recordatorio();
        updateEntityFromDTO(recordatorio, dto);
        return recordatorio;
    }

    private void updateEntityFromDTO(Recordatorio recordatorio, RecordatorioDTO dto) {
        recordatorio.setTitulo(dto.getTitulo());
        recordatorio.setDescripcion(dto.getDescripcion());
        recordatorio.setTipoRecordatorio(dto.getTipoRecordatorio());
        recordatorio.setFechaHora(dto.getFechaHora());
        recordatorio.setRecordatorioStatus(dto.getRecordatorioStatus());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        recordatorio.setUsuario(usuario);

        RegistroMascota mascota = mascotaDatosRepository.findById(dto.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        recordatorio.setMascota(mascota);
    }

    private RecordatorioDTO mapToDTO(Recordatorio recordatorio) {
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
}