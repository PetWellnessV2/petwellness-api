package com.petwellness.security;


import com.petwellness.model.entity.User;
import com.petwellness.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private final UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().name());

        return new UserPrincipal(
                user.getUserId(),
                user.getEmail(),
                user.getContrasena(),
                Collections.singletonList(authority),
                user
        );

    }
}
