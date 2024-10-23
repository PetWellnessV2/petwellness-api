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
import com.petwellness.repository.VeterinarioRepository;
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
    private final VeterinarioRepository veterinarioRepository;

    @Override
    public UserProfileDTO registerUser(UserRegistroDTO userRegistroDTO) {
        return registerUserWhitRole(userRegistroDTO, ERole.CUSTOMER);
    }

    public UserProfileDTO registerVet(UserRegistroDTO userRegistroDTO) {
        return registerUserWhitRole(userRegistroDTO, ERole.VETERINARIO);
    }

    @Override
    public UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID: " + id));
        user.setEmail(userProfileDTO.getEmail());

        if (user.getRole().getName() == ERole.CUSTOMER) {
            user.getCustomer().setNombre(userProfileDTO.getFirstName());
            user.getCustomer().setApellido(userProfileDTO.getLastName());
            user.getCustomer().setShippingAddress(userProfileDTO.getShippingAddress());
            user.getCustomer().setTelefono(userProfileDTO.getTelefono());
            user.getCustomer().setUpdatedAt(LocalDateTime.now());
        }

        if (user.getRole().getName() == ERole.VETERINARIO) {
            Veterinario veterinario = veterinarioRepository.findById(user.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("No existe un veterinario asociado al usuario con ID: " + id));
            user.getCustomer().setNombre(userProfileDTO.getFirstName());
            user.getCustomer().setApellido(userProfileDTO.getLastName());
            user.getCustomer().setShippingAddress(userProfileDTO.getShippingAddress());
            user.getCustomer().setTelefono(userProfileDTO.getTelefono());
            user.getCustomer().setUpdatedAt(LocalDateTime.now());
            veterinario.setUsuario(user);
            veterinario.setInstitucionEducativa(userProfileDTO.getInstitucionEducativa());
            veterinario.setEspecialidad(userProfileDTO.getEspecialidad());
            user.setVeterinario(veterinario);
        }

        User updatedUser = usuarioRepository.save(user);
        return userMapper.toUserProfileDto(updatedUser);
    }

    @Override
    public UserProfileDTO getUserProfileById(Integer id) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con el ID: " + id));
        if (user.getRole() != null && user.getRole().getName() == ERole.VETERINARIO) {
            Veterinario veterinario = veterinarioRepository.findById(user.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("No existe un veterinario asociado al usuario con ID: " + id));
            user.setVeterinario(veterinario);
        }
        return userMapper.toUserProfileDto(user);
    }

    private UserProfileDTO registerUserWhitRole(UserRegistroDTO userRegistroDTO, ERole roleEnum) {
        boolean existingByEmail = usuarioRepository.existsByEmail(userRegistroDTO.getEmail());
        boolean existingAsCustomer = customerRepository.existsByNombreAndApellido(userRegistroDTO.getNombre(), userRegistroDTO.getApellido());
        boolean existingAsVet = customerRepository.existsByNombreAndApellido(userRegistroDTO.getNombre(), userRegistroDTO.getApellido());
        if (userRegistroDTO.getContrasena() == null || userRegistroDTO.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
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
        String encodedPassword = passwordEncoder.encode(userRegistroDTO.getContrasena());

        User user = userMapper.toEntity(userRegistroDTO);
        user.setContrasena(encodedPassword);
        user.setEmail(userRegistroDTO.getEmail());
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
