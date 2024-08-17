package com.example.proyecto_citas_medicas.security;

import com.example.proyecto_citas_medicas.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.debug("Configuring security filter chain");

        http
                .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**", "/api/**git").permitAll() // Permite el acceso a todas las rutas que comiencen con /public/
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra ruta
                )
                .formLogin(form -> {
                    logger.debug("Configuring form login");
                    form
                            .loginPage("/login")
                            .permitAll() // Permite el acceso a la página de login
                            .defaultSuccessUrl("/home", true) // Redirige a la página principal después del login
                            .failureUrl("/login?error"); // Redirige a la página de login en caso de error
                })
                .logout(logout -> {
                    logger.debug("Configuring logout");
                    logout
                            .permitAll(); // Permite el acceso a la funcionalidad de logout
                })
                .userDetailsService(customUserDetailsService); // Configura el servicio personalizado de detalles de usuario

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configura el codificador de contraseñas BCrypt
    }
}