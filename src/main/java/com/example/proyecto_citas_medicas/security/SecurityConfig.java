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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS con la configuración personalizada
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**", "/api/**").permitAll() // Permite el acceso a las rutas públicas
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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*")); // Permite todos los puertos en localhost
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("*")); // Permite todos los encabezados
        configuration.setAllowCredentials(true); // Permite el uso de credenciales como cookies
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configura el codificador de contraseñas BCrypt
    }
}
