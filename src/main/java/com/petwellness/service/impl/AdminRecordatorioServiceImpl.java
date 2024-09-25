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

import com.petwellness.service.AdminRecordatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AdminRecordatorioServiceImpl implements AdminRecordatorioService {

    private final RecordatorioRepository recordatorioRepository;
    private final UsuarioRepository usuarioRepository;
    private final MascotaDatosRepository registroMascotaRepository;

    @Transactional
    @Override
    public RecordatorioDTO createRecordatorio(RecordatorioDTO recordatorioDTO) {
        Recordatorio recordatorio = mapToEntity(recordatorioDTO);
        recordatorio = recordatorioRepository.save(recordatorio);
        return mapToDTO(recordatorio);
    }


    @Transactional
    @Override
    public RecordatorioDTO updateRecordatorio(Integer id, RecordatorioDTO recordatorioDTO) {
        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));
        updateEntityFromDTO(recordatorio, recordatorioDTO);
        recordatorio = recordatorioRepository.save(recordatorio);
        return mapToDTO(recordatorio);
    }

    @Transactional
    @Override
    public void deleteRecordatorio(Integer id) {
        recordatorioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public RecordatorioDTO getRecordatorioById(Integer id) {
        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recordatorio no encontrado"));
        return mapToDTO(recordatorio);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RecordatorioDTO> getAllRecordatorios() {
        return recordatorioRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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

        RegistroMascota mascota = registroMascotaRepository.findById(dto.getMascotaId())
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





    @Override
    @Transactional(readOnly = true)
    public List<Recordatorio> findByUsuarioId(Integer userId) {
        return recordatorioRepository.findByUsuario_UserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recordatorio> paginateByUsuarioId(Integer userId, Pageable pageable) {
        return recordatorioRepository.findByUsuario_UserId(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recordatorio> findByUsuarioIdAndStatus(Integer userId, RecordatorioStatus status) {
        return recordatorioRepository.findByUsuario_UserIdAndRecordatorioStatus(userId, status);
    }



    @Transactional(readOnly = true)
    @Override
    public List<Recordatorio> getAll() {
        return recordatorioRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Recordatorio> paginate(Pageable pageable) {
        return recordatorioRepository.findAll(pageable);
    }






    @Transactional(readOnly = true)
    @Override
    public Recordatorio findById(Integer userId) {
        return recordatorioRepository.findById(userId).
                orElseThrow(()-> new RuntimeException("Recordatorio no encontrado"));
    }

    @Transactional
    @Override
    public Recordatorio create(Recordatorio recordatorio) {
        recordatorio.setFechaHora(LocalDateTime.now());
        return recordatorioRepository.save(recordatorio);
    }

    @Transactional
    @Override
    public Recordatorio update(Integer userId, Recordatorio updatedRecordatorio) {
        Recordatorio recordatorioFromDb = findById(userId);
        recordatorioFromDb.setTitulo(updatedRecordatorio.getTitulo());
        recordatorioFromDb.setDescripcion(updatedRecordatorio.getDescripcion());
        recordatorioFromDb.setFechaHora(updatedRecordatorio.getFechaHora());
        return recordatorioRepository.save(recordatorioFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer userId) {
        Recordatorio recordatorio = findById(userId);
        recordatorioRepository.delete(recordatorio);
    }
}
