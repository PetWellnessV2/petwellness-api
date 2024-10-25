package com.petwellness.service.impl;

import com.petwellness.dto.MedicamentosDTO;
import com.petwellness.dto.MedicamentosRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.MedicamentosRegistroMapper;
import com.petwellness.model.entity.Medicamentos;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.MedicamentosRepository;
import com.petwellness.repository.MascotaDatosRepository;
import com.petwellness.service.MedicamentosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MedicamentosServiceImpl implements MedicamentosService {

    private final MedicamentosRepository medicamentosRepository;
    private final MascotaDatosRepository mascotaDatosRepository;
    private final MedicamentosRegistroMapper medicamentosRegistroMapper;

    @Transactional(readOnly = true)
    @Override
    public List<MedicamentosDTO> getAllMedicamentos() {
        List<Medicamentos> medicamentos = medicamentosRepository.findAll();
        return medicamentos.stream().map(medicamento -> {
            MedicamentosDTO medicamentoDTO = new MedicamentosDTO();
            medicamentoDTO.setIdMedicamento(medicamento.getIdMedicamento());
            medicamentoDTO.setDescripcion(medicamento.getDescripcion());
            medicamentoDTO.setNomMascota(medicamento.getRegistroMascota().getNombre());
            return medicamentoDTO;
        }).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public MedicamentosDTO getMedicamentoById(Integer id) {
        Medicamentos medicamento = medicamentosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El medicamento con ID "+id+" no existe"));
        MedicamentosDTO medicamentoDTO = new MedicamentosDTO();
        medicamentoDTO.setIdMedicamento(medicamento.getIdMedicamento());
        medicamentoDTO.setDescripcion(medicamento.getDescripcion());
        medicamentoDTO.setNomMascota(medicamento.getRegistroMascota().getNombre());
        return medicamentoDTO;
    }

    @Transactional
    @Override
    public MedicamentosRegistroDTO createMedicamento(MedicamentosRegistroDTO medicamentosRegistroDTO) {
        medicamentosRepository.findByDescripcion(medicamentosRegistroDTO.getDescripcion())
                .ifPresent(existingMedicamento ->{
                    throw new BadRequestException("Ya existe un medicamento con la misma descripción");
                });
        Integer idMascota = medicamentosRegistroDTO.getIdRegistroMascota();
        RegistroMascota registroMascota = mascotaDatosRepository.findById(idMascota)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID "+idMascota+" no existe"));
        Medicamentos medicamentos = medicamentosRegistroMapper.toEntity(medicamentosRegistroDTO);
        medicamentos.setFecha(LocalDate.now());
        medicamentos.setRegistroMascota(registroMascota);
        medicamentos = medicamentosRepository.save(medicamentos);
        return medicamentosRegistroMapper.toDTO(medicamentos);
    }

    @Transactional
    @Override
    public MedicamentosRegistroDTO updateMedicamento(Integer id, MedicamentosRegistroDTO medicamentosRegistroDTO) {
        Medicamentos medicamentoFromDB = medicamentosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El medicamento con ID "+id+" no existe"));
        medicamentosRepository.findByDescripcion(medicamentosRegistroDTO.getDescripcion())
                .filter(existingMedicamento -> !existingMedicamento.getIdMedicamento().equals(id))
                .ifPresent(existingMedicamento -> {
                    throw new BadRequestException("Ya existe un medicamento con la misma descripción");
                });

        Integer idMascota = medicamentosRegistroDTO.getIdRegistroMascota();
        RegistroMascota registroMascota = mascotaDatosRepository.findById(idMascota)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID "+idMascota+" no existe"));

        medicamentoFromDB.setDescripcion(medicamentosRegistroDTO.getDescripcion());
        medicamentoFromDB.setFecha(LocalDate.now());
        medicamentoFromDB.setRegistroMascota(registroMascota);
        medicamentoFromDB = medicamentosRepository.save(medicamentoFromDB);
        return medicamentosRegistroMapper.toDTO(medicamentoFromDB);
    }

    @Transactional
    @Override
    public void deleteMedicamento(Integer id) {
        Medicamentos medicamento = medicamentosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El medicamento con ID "+id+" no existe"));
        medicamentosRepository.delete(medicamento);
    }
}

