package com.petwellness.config;

import com.petwellness.security.JWTAuthenticationEntryPoint;
import com.petwellness.security.JWTConfigurer;
import com.petwellness.security.JWTFilter;
import com.petwellness.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Importante para anotaciones
public class WebSecurityConfig {
    private final TokenProvider tokenProvider;
    private final JWTFilter jwtRequestFilter;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(antMatcher("/auth/login")).permitAll()
                        .requestMatchers(antMatcher("/auth/register/customer")).permitAll()
                        .requestMatchers(antMatcher("/auth/register/veterinario")).permitAll()
                        .requestMatchers(antMatcher("/auth/register/albergue")).permitAll()
                        .requestMatchers(antMatcher("/mail/**")).permitAll()
                        .requestMatchers("/api/v1/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html",
                                "/swagger-ui/**", "/webjars/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .with(new JWTConfigurer(tokenProvider), Customizer.withDefaults());

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}