package com.petwellness.service.impl;

import com.petwellness.dto.VeterinarioDTO;
import com.petwellness.dto.VeterinarioRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.VeterinarioMapper;
import com.petwellness.mapper.VeterinarioRegistroMapper;
import com.petwellness.model.entity.Usuario;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.repository.VeterinarioRepository;
import com.petwellness.service.UsuarioService;
import com.petwellness.service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final VeterinarioMapper veterinarioMapper;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final VeterinarioRegistroMapper veterinarioRegistroMapper;

    @Transactional
    public VeterinarioRegistroDTO crearVeterinario(VeterinarioRegistroDTO veterinarioRegistroDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByNombreAndApellido(
                veterinarioRegistroDTO.getNombre(),
                veterinarioRegistroDTO.getApellido()
        );

        if (usuarioExistente.isPresent()) {
            throw new BadRequestException("Ya existe un usuario con el mismo nombre y apellido");
        }
        Veterinario veterinario = veterinarioRegistroMapper.toEntity(veterinarioRegistroDTO);
        Usuario usuario = new Usuario();

        usuario.setNombre(veterinarioRegistroDTO.getNombre());
        usuario.setApellido(veterinarioRegistroDTO.getApellido());
        usuario.setEmail(veterinarioRegistroDTO.getEmail());
        usuario.setTelefono(veterinarioRegistroDTO.getTelefono());
        usuario.setContrasena(veterinarioRegistroDTO.getContrasena());
        usuario.setTipoUsuario(veterinarioRegistroDTO.getTipoUsuario());
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());

        veterinario.setUsuario(usuario);
        veterinario = veterinarioRepository.save(veterinario);

        return veterinarioRegistroMapper.toDTO(veterinario);
    }


    @Transactional(readOnly = true)
    @Override
    public List<VeterinarioDTO> obtenerVeterinarios() {
        List<Veterinario> veterinarios =  veterinarioRepository.findAll();
        return veterinarios.stream()
                .map(
                        veterinario ->{
                            VeterinarioDTO veterinarioDTO = new VeterinarioDTO();
                            veterinarioDTO.setUserId(veterinario.getUsuario_user_id());
                            veterinarioDTO.setApellido(veterinario.getUsuario().getApellido());
                            veterinarioDTO.setNombre(veterinario.getUsuario().getNombre());
                            veterinarioDTO.setEmail(veterinario.getUsuario().getEmail());
                            veterinarioDTO.setTelefono(veterinario.getUsuario().getTelefono());
                            veterinarioDTO.setInstitucionEducativa(veterinario.getInstitucionEducativa());
                            veterinarioDTO.setEspecialidad(veterinario.getEspecialidad());
                            veterinarioDTO.setTipoUsuario(veterinario.getUsuario().getTipoUsuario());
                            return veterinarioDTO;
                        }

                )
                .toList();
    }


    @Transactional
    @Override
    public void eliminarVeterinario(Integer id) {
        if (!veterinarioRepository.existsById(id)) {
            throw new RuntimeException("El veterinario no existe");
        }

        veterinarioRepository.deleteById(id);
    }



    @Transactional
    @Override
    public VeterinarioRegistroDTO actualizarVeterinario(Integer id, VeterinarioRegistroDTO veterinarioRegistroDTO) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El veterinario con ID "+id+" no existe"));

        Usuario usuario = veterinario.getUsuario();

        usuario.setApellido(veterinarioRegistroDTO.getApellido());
        usuario.setNombre(veterinarioRegistroDTO.getNombre());
        usuario.setEmail(veterinarioRegistroDTO.getEmail());
        usuario.setTelefono(veterinarioRegistroDTO.getTelefono());
        usuario.setContrasena(veterinarioRegistroDTO.getContrasena());
        usuario.setTipoUsuario(veterinarioRegistroDTO.getTipoUsuario());
        usuario.setUpdatedAt(LocalDateTime.now());

        veterinario.setUsuario(usuario);
        veterinario.setEspecialidad(veterinarioRegistroDTO.getEspecialidad());
        veterinario.setInstitucionEducativa(veterinarioRegistroDTO.getInstitucionEducativa());
        veterinario = veterinarioRepository.save(veterinario);

        return veterinarioRegistroMapper.toDTO(veterinario);

    }
}


