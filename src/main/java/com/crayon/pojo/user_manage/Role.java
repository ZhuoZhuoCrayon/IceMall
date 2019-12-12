package com.crayon.pojo.user_manage;

import java.io.Serializable;

public class Role implements Serializable {
    private Integer id;
    private String role;
    private String description;

    public Role(){}
    public Role(Integer id,String role,String description){
        this.id = id;
        this.role = role;
        this.description = description;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getRole() {
        return role;
    }
}