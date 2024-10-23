package com.petwellness.service.impl;

import com.petwellness.dto.UserProfileDTO;
import com.petwellness.dto.UserRegistroDTO;
import com.petwellness.mapper.UserMapper;
import com.petwellness.model.entity.Customer;
import com.petwellness.model.entity.Role;
import com.petwellness.model.entity.User;
import com.petwellness.model.entity.Veterinario;
import com.petwellness.model.enums.ERole;
import com.petwellness.repository.CustomerRepository;
import com.petwellness.repository.RoleRepository;
import com.petwellness.repository.UsuarioRepository;
import com.petwellness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsuarioRepository usuarioRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserProfileDTO registerUser(UserRegistroDTO userRegistroDTO) {
        return registerUserWhitRole(userRegistroDTO, ERole.CUSTOMER);
    }

    public UserProfileDTO registerVet(UserRegistroDTO userRegistroDTO) {
        return registerUserWhitRole(userRegistroDTO, ERole.VETERINARIO);
    }

    @Override
    public UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO) {
        return null;
    }

    @Override
    public UserProfileDTO getUserProfileById(Integer id) {
        return null;
    }

    private UserProfileDTO registerUserWhitRole(UserRegistroDTO userRegistroDTO, ERole roleEnum) {
        boolean existingByEmail = usuarioRepository.existsByEmail(userRegistroDTO.getEmail());
        boolean existingAsCustomer = customerRepository.existsByNombreAndApellido(userRegistroDTO.getNombre(), userRegistroDTO.getApellido());
        boolean existingAsVet = customerRepository.existsByNombreAndApellido(userRegistroDTO.getNombre(), userRegistroDTO.getApellido());
        if (existingByEmail) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (existingAsCustomer) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre y apellido");
        }
        if (existingAsVet) {
            throw new IllegalArgumentException("Ya existe un veterinario con ese nombre y apellido");
        }
        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new RuntimeException("No existe un role en el usuario"));
        userRegistroDTO.setContrasena(passwordEncoder.encode(userRegistroDTO.getContrasena()));

        User user = userMapper.toEntity(userRegistroDTO);
        user.setRole(role);
        if (roleEnum == ERole.CUSTOMER) {
            Customer customer = new Customer();
            customer.setNombre(userRegistroDTO.getNombre());
            customer.setApellido(userRegistroDTO.getApellido());
            customer.setTelefono(userRegistroDTO.getTelefono());
            customer.setShippingAddress(userRegistroDTO.getShippingAddress());
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUpdatedAt(LocalDateTime.now());
            customer.setUser(user);
            user.setCustomer(customer);
        } else if (roleEnum == ERole.VETERINARIO) {
            // Crear Customer
            Customer customer = new Customer();
            customer.setNombre(userRegistroDTO.getNombre());
            customer.setApellido(userRegistroDTO.getApellido());
            customer.setTelefono(userRegistroDTO.getTelefono());
            customer.setShippingAddress(userRegistroDTO.getShippingAddress());
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUpdatedAt(LocalDateTime.now());
            customer.setUser(user);
            user.setCustomer(customer);
            Veterinario veterinario = new Veterinario();
            veterinario.setUsuario(user);
            veterinario.setEspecialidad(userRegistroDTO.getEspecialidad());
            veterinario.setInstitucionEducativa(userRegistroDTO.getInstitucionEducativa());
            user.setVeterinario(veterinario);
        }
        User saveUser = usuarioRepository.save(user);
        return userMapper.toUserProfileDto(saveUser);
    }
}
