package com.example.proyecto_citas_medicas.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.proyecto_citas_medicas.dtos.LoginUserDto;
import com.example.proyecto_citas_medicas.dtos.RegisterUserDto;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.entities.UserRoles;
import com.example.proyecto_citas_medicas.repository.UserRepository;
import com.example.proyecto_citas_medicas.repository.UserRolesRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private static final Long DEFAULT_ROLE_ID = 1L;

    public AuthenticationService(
        UserRepository userRepository,
        UserRolesRepository userRolesRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setStatus('A');
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);
    }

    public UserRoles setUserRole(User user) {
        UserRoles userRole = new UserRoles();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(DEFAULT_ROLE_ID);
        userRole.setStatus('A');

        return userRolesRepository.save(userRole);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        String email = input.getEmail();

        return userRepository.findByEmail(email)
                .orElseThrow();
    }
}