package com.example.proyecto_citas_medicas.dtos;

public class RecoverPasswordDto {
    private String email;
    private String password;
    private String confirm_password;
    
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getNewPassword(){
        return password;
    }
    public void setNewPassword(String password){
        this.password = password;
    }

    public String getConfirmPassword(){
        return confirm_password;
    }
    public void setConfirmPassword(String confirm_password){
        this.confirm_password = confirm_password;
    }
}
