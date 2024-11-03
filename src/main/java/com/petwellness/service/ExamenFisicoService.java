package com.petwellness.service;

import com.petwellness.dto.ExamenFisicoDTO;

import java.util.List;

public interface ExamenFisicoService {
    List<ExamenFisicoDTO> findExamenesFisicosByMascotaId(Integer mascotaId);
    ExamenFisicoDTO crearExamenFisico(ExamenFisicoDTO examenFisicoDTO);
    ExamenFisicoDTO actualizarExamenFisico(Integer id, ExamenFisicoDTO examenFisicoDTO);
    List<ExamenFisicoDTO> obtenerExamenesFisicos();
    ExamenFisicoDTO obtenerExamenFisicoPorId(Integer id);
    void eliminarExamenFisico(Integer id);
}
