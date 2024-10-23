package com.petwellness.service.impl;

import com.petwellness.dto.UsuarioDTO;
import com.petwellness.dto.UsuarioRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.UsuarioMapper;
import com.petwellness.mapper.UsuarioRegistroMapper;
import com.petwellness.model.entity.Customer;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioRegistroMapper usuarioRegistroMapper;

    @Transactional
    @Override
    public UsuarioRegistroDTO registerUsuario(Customer usuarioRegistroDTO) {
        /*usuarioRepository.findByNombreAndApellido(usuarioRegistroDTO.getNombre(), usuarioRegistroDTO.getApellido())
                .ifPresent(existingUsuario ->{
                    throw new BadRequestException("Ya existe un usuario con el mismo nombre y apellido");
                });

         */
        Customer usuario = usuarioRegistroMapper.toEntity(usuarioRegistroDTO);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());
        //usuario = usuarioRepository.save(usuario);
        return usuarioRegistroMapper.toDTO(usuario);

    }

    @Transactional
    @Override
    public UsuarioRegistroDTO updateUsuario(Integer id, UsuarioRegistroDTO usuarioRegistroDTO) {
        /*Customer usuarioFromDB = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID "+id+" no existe"));*/
        /*usuarioRepository.findByNombreAndApellido(usuarioRegistroDTO.getNombre(), usuarioRegistroDTO.getApellido())
                .filter(existingUsuario -> !existingUsuario.getUserId().equals(id))
                .ifPresent(existingUsuario -> {
                    throw new BadRequestException("Ya existe un usuario con el mismo id");
                });

         */
        /*
        usuarioFromDB.setNombre(usuarioRegistroDTO.getNombre());
        usuarioFromDB.setApellido(usuarioRegistroDTO.getApellido());
        usuarioFromDB.setEmail(usuarioRegistroDTO.getEmail());
        usuarioFromDB.setTelefono(usuarioRegistroDTO.getTelefono());
        usuarioFromDB.setContrasena(usuarioRegistroDTO.getContrasena());
        usuarioFromDB.setTipoUsuario(usuarioRegistroDTO.getTipoUsuario());
        usuarioFromDB.setUpdatedAt(LocalDateTime.now());

        usuarioFromDB = usuarioRepository.save(usuarioFromDB);
        */
        return null; //usuarioRegistroMapper.toDTO(usuarioFromDB);
    }

    @Transactional
    @Override
    public void deleteUsuario(Integer id) {
        /*Customer usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID "+id+" no existe"));
        usuarioRepository.delete(usuario);
         */
    }

    @Transactional(readOnly = true)
    @Override
    public List<UsuarioDTO> getAllUsuarios() {
        /*List<Customer> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuarioMapper::toDTO).toList();
         */
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public UsuarioDTO getUsuarioById(Integer id) {
        /*Customer usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID "+id+" no existe"));
        return usuarioMapper.toDTO(usuario);
         */
        return null;
    }
}
