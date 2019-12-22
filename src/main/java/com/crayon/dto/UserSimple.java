package com.crayon.dto;

import java.util.HashMap;

public class UserSimple {
    private String userName;
    private String role;
    private HashMap<String,Object> userParams;

    public String getRole() {
        return role;
    }

    public String getUserName() {
        return userName;
    }

    public HashMap<String, Object> getUserParams() {
        return userParams;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserParams(HashMap<String, Object> userParams) {
        this.userParams = userParams;
    }
}
