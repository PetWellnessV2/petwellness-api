package com.petwellness.service.impl;

import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;
import com.petwellness.dto.UsuarioDTO;
import com.petwellness.dto.UsuarioRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.UserMapper;
import com.petwellness.mapper.UsuarioMapper;
import com.petwellness.mapper.UsuarioRegistroMapper;
import com.petwellness.model.entity.Customer;
import com.petwellness.model.entity.User;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.model.enums.ERole;
import com.petwellness.model.enums.TipoUser;
import com.petwellness.repository.CustomerRepository;
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
    private final CustomerRepository customerRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioRegistroMapper usuarioRegistroMapper;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UsuarioRegistroDTO registerUsuario(UserRegistroDTO usuarioRegistroDTO) {
        customerRepository.findByNombreAndApellido(usuarioRegistroDTO.getNombre(), usuarioRegistroDTO.getApellido())
                .ifPresent(existingUsuario -> {
                    throw new BadRequestException("Ya existe un usuario con el mismo nombre y apellido");
                });


        User usuario = new User();
        Customer user = new Customer();
        user.setNombre(usuarioRegistroDTO.getNombre());
        user.setApellido(usuarioRegistroDTO.getApellido());
        user.setTelefono(usuarioRegistroDTO.getTelefono());
        user.setTipoUsuario(TipoUser.CUSTOMER);
        user.setShippingAddress(usuarioRegistroDTO.getShippingAddress());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        usuario.setCustomer(user);
        usuario.setEmail(usuarioRegistroDTO.getEmail());
        usuario.setContrasena(usuarioRegistroDTO.getContrasena());
        user.setUser(usuario);
        return usuarioRegistroMapper.toDTO(user);

    }

    @Transactional
    @Override
    public UsuarioRegistroDTO updateUsuario(Integer id, UsuarioRegistroDTO usuarioRegistroDTO) {
        User usuarioFromDB = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID "+id+" no existe"));
        customerRepository.findByNombreAndApellido(usuarioRegistroDTO.getNombre(), usuarioRegistroDTO.getApellido())
                .filter(existingUsuario -> !existingUsuario.getUserId().equals(id))
                .ifPresent(existingUsuario -> {
                    throw new BadRequestException("Ya existe un usuario con el mismo id");
                });
        Customer usuario = usuarioFromDB.getCustomer();
        usuario.setNombre(usuarioRegistroDTO.getNombre());
        usuario.setApellido(usuarioRegistroDTO.getApellido());
        usuario.setTelefono(usuarioRegistroDTO.getTelefono());
        usuario.setTipoUsuario(usuarioRegistroDTO.getTipoUsuario());
        usuario.setUpdatedAt(LocalDateTime.now());
        usuarioFromDB.setCustomer(usuario);
        if(usuarioFromDB.getRole().getName() == ERole.VETERINARIO){
            usuarioFromDB.setEmail(usuarioRegistroDTO.getEmail());
            usuarioFromDB.setContrasena(usuarioRegistroDTO.getContrasena());
        }
        usuarioFromDB = usuarioRepository.save(usuarioFromDB);
        usuario.setUser(usuarioFromDB);
        return usuarioRegistroMapper.toDTO(usuario);
    }

    @Transactional
    @Override
    public void deleteUsuario(Integer id) {
        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID "+id+" no existe"));
        usuarioRepository.delete(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserProfileDTO> getAllUsuarios() {
        List<User> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(userMapper::toUserProfileDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfileDTO getUsuarioById(Integer id) {
        User usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID "+id+" no existe"));
        return userMapper.toUserProfileDto(usuario);
    }
}
