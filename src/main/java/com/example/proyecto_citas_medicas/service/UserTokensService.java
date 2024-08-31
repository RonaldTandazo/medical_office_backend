package com.example.proyecto_citas_medicas.service;

import org.springframework.stereotype.Service;
import com.example.proyecto_citas_medicas.entities.UserTokens;
import com.example.proyecto_citas_medicas.repository.UserTokensRepository;

@Service
public class UserTokensService {

    private final UserTokensRepository userTokensRepository;

    public UserTokensService(UserTokensRepository userTokensRepository){
        this.userTokensRepository = userTokensRepository;
    }

    public UserTokens findItemByUserId(Long user_id){
        return userTokensRepository.findItemByUserId(user_id).orElse(null);
    }

    public UserTokens findItemByToken(String reset_token){
        return userTokensRepository.findItemByToken(reset_token).orElse(null);
    }

    public UserTokens insertItem(Long user_id, String reset_token){
        return userTokensRepository.insertItem(user_id, reset_token);
    }

    public UserTokens updateItem(Long user_token_id, String reset_token){
        return userTokensRepository.updateItem(user_token_id, reset_token);
    }
}
