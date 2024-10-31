package com.petwellness.service.impl;

import com.petwellness.dto.VeterinarioDTO;
import com.petwellness.dto.VeterinarioRegistroDTO;
import com.petwellness.exception.BadRequestException;
import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.mapper.VeterinarioMapper;
import com.petwellness.mapper.VeterinarioRegistroMapper;
import com.petwellness.model.entity.Customer;
import com.petwellness.model.entity.User;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.repository.CustomerRepository;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.repository.VeterinarioRepository;
import com.petwellness.service.UsuarioService;
import com.petwellness.service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final VeterinarioMapper veterinarioMapper;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final VeterinarioRegistroMapper veterinarioRegistroMapper;
    private final CustomerRepository customerRepository;

    @Transactional
    public VeterinarioRegistroDTO crearVeterinario(VeterinarioRegistroDTO veterinarioRegistroDTO) {
        customerRepository.findByNombreAndApellido(veterinarioRegistroDTO.getNombre(), veterinarioRegistroDTO.getApellido())
                .ifPresent(existingUsuario ->{
                    throw new BadRequestException("Ya existe un veterinario con el mismo nombre y apellido");
                });
        Veterinario veterinario = veterinarioRegistroMapper.toEntity(veterinarioRegistroDTO);
        Customer usuario = new Customer();
        User user = new User();
        usuario.setNombre(veterinarioRegistroDTO.getNombre());
        usuario.setApellido(veterinarioRegistroDTO.getApellido());
        usuario.setTelefono(veterinarioRegistroDTO.getTelefono());
        usuario.setTipoUsuario(veterinarioRegistroDTO.getTipoUsuario());
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());
        user.setEmail(veterinarioRegistroDTO.getEmail());
        user.setContrasena(veterinarioRegistroDTO.getContrasena());
        user.setCustomer(usuario);
        veterinario.setEspecialidad(veterinarioRegistroDTO.getEspecialidad());
        veterinario.setInstitucionEducativa(veterinarioRegistroDTO.getInstitucionEducativa());
        veterinario.setVet(user);
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
                            veterinarioDTO.setApellido(veterinario.getVet().getCustomer().getApellido());
                            veterinarioDTO.setNombre(veterinario.getVet().getCustomer().getNombre());
                            veterinarioDTO.setEmail(veterinario.getVet().getEmail());
                            veterinarioDTO.setTelefono(veterinario.getVet().getCustomer().getTelefono());
                            veterinarioDTO.setInstitucionEducativa(veterinario.getInstitucionEducativa());
                            veterinarioDTO.setEspecialidad(veterinario.getEspecialidad());
                            veterinarioDTO.setTipoUsuario(veterinario.getVet().getCustomer().getTipoUsuario());
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
        Customer usuario = veterinario.getVet().getCustomer();
        usuario.setApellido(veterinarioRegistroDTO.getApellido());
        usuario.setNombre(veterinarioRegistroDTO.getNombre());
        usuario.setTelefono(veterinarioRegistroDTO.getTelefono());
        usuario.setTipoUsuario(veterinarioRegistroDTO.getTipoUsuario());
        usuario.setUpdatedAt(LocalDateTime.now());
        User user = veterinario.getVet();
        user.setEmail(veterinarioRegistroDTO.getEmail());
        user.setContrasena(veterinarioRegistroDTO.getContrasena());
        user.setCustomer(usuario);
        veterinario.setEspecialidad(veterinarioRegistroDTO.getEspecialidad());
        veterinario.setInstitucionEducativa(veterinarioRegistroDTO.getInstitucionEducativa());
        user.setVeterinario(veterinario);
        veterinario.setVet(user);
        veterinario = veterinarioRepository.save(veterinario);
        return veterinarioRegistroMapper.toDTO(veterinario);

    }
}


