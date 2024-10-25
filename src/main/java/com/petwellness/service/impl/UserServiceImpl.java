package com.petwellness.service.impl;

import com.petwellness.dto.AuthResponseDTO;
import com.petwellness.dto.LoginDTO;
import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegisterDTO;
import com.petwellness.exception.RoleNotFoundException;
import com.petwellness.mapper.UserMapper;
import com.petwellness.model.entity.Albergue;
import com.petwellness.model.entity.Cliente;
import com.petwellness.model.entity.Role;
import com.petwellness.model.entity.User;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.model.enums.ERole;
import com.petwellness.repository.*;
import com.petwellness.security.TokenProvider;
import com.petwellness.security.UserPrincipal;
import com.petwellness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final VeterinarioRepository vetRepository;
    private final AlbergueRepository albeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public UserProfileDTO registerCliente(UserRegisterDTO userRegistroDTO) {
        return registerUserWhitRole(userRegistroDTO, ERole.CUSTOMER);
    }

    @Transactional
    @Override
    public UserProfileDTO registerVeterinario(UserRegisterDTO userRegistroDTO) {
        return registerUserWhitRole(userRegistroDTO, ERole.VETERINARIO);
    }

    @Transactional
    @Override
    public UserProfileDTO registerAlbergue(UserRegisterDTO userRegistroDTO) {
        return registerUserWhitRole(userRegistroDTO, ERole.ALBERGUE);
    }

    @Transactional
    @Override
    public UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID: " + id));

        boolean existingAsCustomer = customerRepository.existsByNombreAndApellido(userProfileDTO.getFirstName(),
                userProfileDTO.getLastName());
        boolean existingAsVet = vetRepository.existsByNombreAndApellido(userProfileDTO.getFirstName(),
                userProfileDTO.getLastName());
        boolean existingAsAlbergue = albeRepository.existsByRuc(userProfileDTO.getRuc());

        if (existingAsCustomer || existingAsVet) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre y apellido");
        } else if (existingAsAlbergue) {
            throw new IllegalArgumentException("Ya existe un albergue registrado con ese RUC");
        }

        if (user.getCliente() != null) {
            user.getCliente().setNombre(userProfileDTO.getFirstName());
            user.getCliente().setApellido(userProfileDTO.getLastName());
            user.getCliente().setShippingAddress(userProfileDTO.getShippingAddress());
            user.getCliente().setTelefono(userProfileDTO.getTelefono());
            user.getCliente().setUpdatedAt(LocalDateTime.now());
        }

        if (user.getVeterinario() != null) {
            user.getVeterinario().setNombre(userProfileDTO.getFirstName());
            user.getVeterinario().setApellido(userProfileDTO.getLastName());
            user.getVeterinario().setEspecialidad(userProfileDTO.getEspecialidad());
            user.getVeterinario().setInstitucionEducativa(userProfileDTO.getInstitucionEducativa());
        }

        if (user.getAlbergue() != null) {
            user.getAlbergue().setRuc(userProfileDTO.getRuc());
            user.getAlbergue().setNombreAlbergue(userProfileDTO.getNombreAlbergue());
            user.getAlbergue().setRuc(userProfileDTO.getRuc());
            user.getAlbergue().setTipoAlbergue(userProfileDTO.getTipoAlbergue());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(updatedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfileDTO getUserProfileById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID: " + id));
        return userMapper.toUserProfileDTO(user);
    }

    private UserProfileDTO registerUserWhitRole(UserRegisterDTO userRegistroDTO, ERole roleEnum) {
        boolean existingByEmail = userRepository.existsByEmail(userRegistroDTO.getEmail());
        boolean existingAsCustomer = customerRepository.existsByNombreAndApellido(userRegistroDTO.getNombre(),
                userRegistroDTO.getApellido());
        boolean existingAsVet = vetRepository.existsByNombreAndApellido(userRegistroDTO.getNombre(),
                userRegistroDTO.getApellido());
        boolean existingAsAlbergue = albeRepository.existsByRuc(userRegistroDTO.getRuc());
        if (userRegistroDTO.getContrasena() == null || userRegistroDTO.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (existingByEmail) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (existingAsCustomer || existingAsVet) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre y apellido");
        }
        if (existingAsAlbergue) {
            throw new IllegalArgumentException("Ya existe un albergue registrado con ese RUC");
        }

        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new RoleNotFoundException("Rol no encontrado: " + roleEnum));

        userRegistroDTO.setContrasena(passwordEncoder.encode(userRegistroDTO.getContrasena()));

        User user = userMapper.toEntity(userRegistroDTO);
        user.setRole(role);

        if (roleEnum == ERole.CUSTOMER) {
            Cliente customer = new Cliente();
            customer.setNombre(userRegistroDTO.getNombre());
            customer.setApellido(userRegistroDTO.getApellido());
            customer.setTelefono(userRegistroDTO.getTelefono());
            customer.setShippingAddress(userRegistroDTO.getShippingAddress());
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUpdatedAt(LocalDateTime.now());
            customer.setUser(user);
            user.setCliente(customer);

        } else if (roleEnum == ERole.VETERINARIO) {
            Veterinario vet = new Veterinario();
            vet.setNombre(userRegistroDTO.getNombre());
            vet.setApellido(userRegistroDTO.getApellido());
            vet.setInstitucionEducativa(userRegistroDTO.getInstitucionEducativa());
            vet.setEspecialidad(userRegistroDTO.getEspecialidad());
            vet.setUser(user);
            user.setVeterinario(vet);

        } else if (roleEnum == ERole.ALBERGUE) {

            Albergue albergue = new Albergue();
            albergue.setRuc(userRegistroDTO.getRuc());
            albergue.setNombreAlbergue(userRegistroDTO.getNombreAlbergue());
            albergue.setTipoAlbergue(userRegistroDTO.getTipoAlbergue());
            albergue.setUser(user);
            user.setAlbergue(albergue);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(savedUser);
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        // Autenticar al usuario utilizando AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        String token = tokenProvider.createAccessToken(authentication);

        AuthResponseDTO responseDTO = userMapper.toAuthResponseDTO(user, token);

        return responseDTO;
    }
}
