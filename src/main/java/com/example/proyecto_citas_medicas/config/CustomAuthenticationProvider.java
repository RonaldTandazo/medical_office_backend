package com.example.proyecto_citas_medicas.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        // Aquí podrías agregar lógica para verificar el usuario, si es necesario
        // Por ejemplo, verificar en la base de datos si el usuario existe

        // Por ahora, solo permitimos la autenticación si el nombre de usuario no está vacío
        if (username != null && !username.isEmpty()) {
            UserDetails userDetails = User.builder()
                    .username(username)
                    .password("") // No se usa la contraseña
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("USER")))
                    .build();
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
