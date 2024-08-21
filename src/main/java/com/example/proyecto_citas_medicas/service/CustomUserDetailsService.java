package com.example.proyecto_citas_medicas.service;

import com.example.proyecto_citas_medicas.entities.Paciente;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario por nombre de usuario
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Manejar el caso en que el usuario no se encuentra
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        // Obtener el usuario de la envoltura Optional
        User user = userOptional.get();

        // Devolver un UserDetails con la información del usuario
        // Nota: Utilizamos una contraseña dummy ya que la contraseña no será validada
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                "dummy", // Contraseña dummy, no se valida
                new ArrayList<>()
        );
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
