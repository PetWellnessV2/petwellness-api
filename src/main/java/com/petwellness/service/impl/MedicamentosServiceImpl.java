package com.petwellness.service.impl;

import com.petwellness.dto.MedicamentosDTO;
import com.petwellness.model.entity.Medicamentos;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.MedicamentosRepository;
import com.petwellness.repository.MascotaDatosRepository;
import com.petwellness.service.MedicamentosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MedicamentosServiceImpl implements MedicamentosService {

    private final MedicamentosRepository medicamentosRepository;
    private final MascotaDatosRepository mascotaDatosRepository;

    @Override
    public List<MedicamentosDTO> getAllMedicamentos() {
        return medicamentosRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentosDTO getMedicamentoById(Integer id) {
        Medicamentos medicamento = medicamentosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        return convertToDTO(medicamento);
    }

    @Transactional
    @Override
    public MedicamentosDTO createMedicamento(MedicamentosDTO medicamentoDTO) {
        Medicamentos medicamento = convertToEntity(medicamentoDTO);
        Medicamentos nuevoMedicamento = medicamentosRepository.save(medicamento);
        return convertToDTO(nuevoMedicamento);
    }

    @Transactional
    @Override
    public MedicamentosDTO updateMedicamento(Integer id, MedicamentosDTO medicamentoDTO) {
        Medicamentos medicamentoExistente = medicamentosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        medicamentoExistente.setDescripcion(medicamentoDTO.getDescripcion());
        medicamentoExistente.setFecha(medicamentoDTO.getFecha());

        RegistroMascota registroMascota = mascotaDatosRepository.findById(medicamentoDTO.getIdMascota())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        medicamentoExistente.setRegistroMascota(registroMascota);

        Medicamentos medicamentoActualizado = medicamentosRepository.save(medicamentoExistente);
        return convertToDTO(medicamentoActualizado);
    }

    @Transactional
    @Override
    public void deleteMedicamento(Integer id) {
        Medicamentos medicamento = medicamentosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        medicamentosRepository.delete(medicamento);
    }

    private MedicamentosDTO convertToDTO(Medicamentos medicamento) {
        MedicamentosDTO dto = new MedicamentosDTO();
        dto.setIdMedicamento(medicamento.getIdMedicamento());
        dto.setDescripcion(medicamento.getDescripcion());
        dto.setFecha(medicamento.getFecha());
        dto.setIdMascota(medicamento.getRegistroMascota().getIdMascota());
        return dto;
    }

    private Medicamentos convertToEntity(MedicamentosDTO dto) {
        Medicamentos medicamento = new Medicamentos();
        medicamento.setDescripcion(dto.getDescripcion());
        medicamento.setFecha(dto.getFecha());

        RegistroMascota registroMascota = mascotaDatosRepository.findById(dto.getIdMascota())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        medicamento.setRegistroMascota(registroMascota);
        return medicamento;
    }
}
