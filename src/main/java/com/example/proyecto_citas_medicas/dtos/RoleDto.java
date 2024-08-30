package com.example.proyecto_citas_medicas.dtos;

public class RoleDto {

    private Long role_id;
    private String name;
    
    public Long getRoleId(){
        return role_id;
    }
    public void setRoleId(Long role_id){
        this.role_id = role_id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
