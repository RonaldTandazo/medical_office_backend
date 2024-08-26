package com.example.proyecto_citas_medicas.service;

import com.example.proyecto_citas_medicas.entities.Role;
import com.example.proyecto_citas_medicas.entities.User;
import com.example.proyecto_citas_medicas.entities.UserTokens;
import com.example.proyecto_citas_medicas.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


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

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Role.RoleProjection> getUserRoles(BigInteger user_id) {
        return userRepository.findByRolesByUserId(user_id);
    }

    public Optional<UserTokens.UsersTokensProjection> findUserToken(BigInteger user_id){
        return userRepository.findUserTokenByUserId(user_id);
    }

    public Optional<UserTokens.UsersTokensProjection> storeItem(BigInteger user_id, String reset_token){
        return userRepository.insertItem(user_id, reset_token);
    }

    public Optional<UserTokens.UsersTokensProjection> updateUserToken(BigInteger user_token_id, String reset_token){
        logger.info(user_token_id.toString());
        logger.info(reset_token);
        return userRepository.updateUserResetToken(user_token_id, reset_token);
    }

    public Optional<UserTokens.UsersTokensProjection> findUserByToken(String reset_token){
        return userRepository.findUserByToken(reset_token);
    }

    public Optional<User> updatePassword(BigInteger user_id, String new_password){
        return userRepository.updatePassword(user_id, new_password);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
