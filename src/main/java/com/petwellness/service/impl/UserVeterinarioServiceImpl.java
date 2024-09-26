package com.petwellness.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.petwellness.dto.RegistroMascotaDTO;
import com.petwellness.mapper.RegistroMascotaMapper;
import com.petwellness.model.entity.Consulta;
import com.petwellness.model.entity.ExamenFisico;
import com.petwellness.model.entity.ExamenesLaboratorio;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.repository.UserVeterinarioRepository;
import com.petwellness.service.UserVeterinarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserVeterinarioServiceImpl implements UserVeterinarioService {

    private final UserVeterinarioRepository veterinarioRepository;
    private final RegistroMascotaMapper registroMascotaMapper;

    @Override
    public List<RegistroMascotaDTO> findRegistroMascotasByUsuario_user_id(Integer usuario_user_id) {
        List<RegistroMascota> mascotas = veterinarioRepository.findRegistroMascotasByUsuario_user_id(usuario_user_id);
        return mascotas.stream()
                .map(registroMascotaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Consulta> findConsultasByMascotaAndUsuarioUserId(Integer mascotaId, Integer usuarioUserId) {
        return veterinarioRepository.findConsultasByMascotaAndUsuarioUserId(mascotaId, usuarioUserId);
    }

    @Override
    public List<ExamenFisico> findExamenesFisicosByMascotaAndUsuarioUserId(Integer mascotaId, Integer usuarioUserId) {
        return veterinarioRepository.findExamenesFisicosByMascotaAndUsuarioUserId(mascotaId, usuarioUserId);
    }

    @Override
    public List<ExamenesLaboratorio> findExamenesLaboratorioByMascotaAndUsuarioUserId(Integer mascotaId,
            Integer usuarioUserId) {
        return veterinarioRepository.findExamenesLaboratorioByMascotaAndUsuarioUserId(mascotaId, usuarioUserId);
    }

}
