package com.petwellness.security;


import com.petwellness.exception.RoleNotFoundException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.validity-in-seconds}")
    private long jwtValidityInSeconds;

    private Key key;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        // Generar la clave para firmar el JWT a partir del secreto configurado
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

        // Inicializar el parser JWT con la clave generada para firmar y validar tokens
        jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String createAccessToken(Authentication authentication) {
        String email = authentication.getName();
        // Obtener el usuario desde el contexto de autenticación
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Integer userId = userPrincipal.getId();  // Obtener el ID del usuario

        String role = authentication
                .getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(RoleNotFoundException::new)
                .getAuthority();

        return Jwts
                .builder()
                .setSubject(email)  // El sujeto del token es el email o nombre de usuario
                .claim("role", role)  // El rol se incluye como claim en el token
                .signWith(key, SignatureAlgorithm.HS512)  // Firmar el token con el algoritmo HS512 y la clave
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInSeconds * 1000))  // Configurar la fecha de expiración del token
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        String role = claims.get("role").toString();

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;  // El token es válido
        } catch (JwtException e) {
            return false;  // El token no es válido
        }
    }
}
