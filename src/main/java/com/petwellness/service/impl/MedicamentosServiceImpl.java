package com.petwellness.service.impl;

import com.petwellness.dto.MedicamentosProfileDTO;
import com.petwellness.dto.MedicamentosRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.MedicamentosMapper;
import com.petwellness.model.entity.Medicamentos;
import com.petwellness.model.entity.Mascota;
import com.petwellness.repository.MedicamentosRepository;
import com.petwellness.repository.MascotaRepository;
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
    private final MascotaRepository mascotaRepository;
    private final MedicamentosMapper medicamentosMapper;

    @Transactional(readOnly = true)
    @Override
    public List<MedicamentosProfileDTO> getAllMedicamentos() {
        List<Medicamentos> medicamentos = medicamentosRepository.findAll();
        return medicamentos.stream().map(medicamento -> {
            MedicamentosProfileDTO medicamentoDTO = new MedicamentosProfileDTO();
            medicamentoDTO.setNombre(medicamento.getNombre());
            medicamentoDTO.setIdMedicamento(medicamento.getIdMedicamento());
            medicamentoDTO.setDescripcion(medicamento.getDescripcion());
            medicamentoDTO.setNomMascota(medicamento.getMascota().getNombre());
            return medicamentoDTO;
        }).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public MedicamentosProfileDTO getMedicamentoById(Integer id) {
        Medicamentos medicamento = medicamentosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El medicamento con ID "+id+" no existe"));
        MedicamentosProfileDTO medicamentoDTO = new MedicamentosProfileDTO();
        medicamentoDTO.setNombre(medicamento.getNombre());
        medicamentoDTO.setIdMedicamento(medicamento.getIdMedicamento());
        medicamentoDTO.setDescripcion(medicamento.getDescripcion());
        medicamentoDTO.setNomMascota(medicamento.getMascota().getNombre());
        return medicamentoDTO;
    }

    @Transactional
    @Override
    public MedicamentosProfileDTO createMedicamento(MedicamentosRegistroDTO medicamentosRegistroDTO) {
        Integer idMascota = medicamentosRegistroDTO.getIdMascota();
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID " + idMascota + " no existe"));
        Medicamentos medicamentos = medicamentosMapper.toEntity(medicamentosRegistroDTO);
        medicamentos.setFecha(LocalDate.now());
        medicamentos.setMascota(mascota);
        medicamentos = medicamentosRepository.save(medicamentos);
        return medicamentosMapper.toDTO(medicamentos);
    }


    @Transactional
    @Override
    public MedicamentosProfileDTO updateMedicamento(Integer id, MedicamentosRegistroDTO medicamentosRegistroDTO) {
        Medicamentos medicamentoFromDB = medicamentosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El medicamento con ID "+id+" no existe"));
        medicamentosRepository.findByDescripcion(medicamentosRegistroDTO.getDescripcion())
                .filter(existingMedicamento -> !existingMedicamento.getIdMedicamento().equals(id))
                .ifPresent(existingMedicamento -> {
                    throw new BadRequestException("Ya existe un medicamento con la misma descripción");
                });

        Integer idMascota = medicamentosRegistroDTO.getIdMascota();
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new ResourceNotFoundException("La mascota con ID "+idMascota+" no existe"));

        medicamentoFromDB.setDescripcion(medicamentosRegistroDTO.getDescripcion());
        medicamentoFromDB.setFecha(LocalDate.now());
        medicamentoFromDB.setMascota(mascota);
        medicamentoFromDB = medicamentosRepository.save(medicamentoFromDB);
        return medicamentosMapper.toDTO(medicamentoFromDB);
    }

    @Transactional
    @Override
    public void deleteMedicamento(Integer id) {
        Medicamentos medicamento = medicamentosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El medicamento con ID "+id+" no existe"));
        medicamentosRepository.delete(medicamento);
    }
}

