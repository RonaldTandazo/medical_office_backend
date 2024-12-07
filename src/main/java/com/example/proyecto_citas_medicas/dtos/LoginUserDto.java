package com.example.proyecto_citas_medicas.dtos;

public class LoginUserDto {
    private String email;
    private Long roleId;
    private String password;
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public Long getRoleId(){
        return roleId;
    }
    public void setRoleId(Long roleId){
        this.roleId = roleId;
    }
}