package com.crayon.dto;

import java.io.Serializable;

public class CusSimple implements Serializable {
    private String userName;
    private String role;
    private String levelName;
    private Float levelDiscount;

    public CusSimple(){}
    public CusSimple(String userName,String role,String levelName,Float levelDiscount){
        this.userName = userName;
        this.role = role;
        this.levelName = levelName;
        this.levelDiscount = levelDiscount;
    }
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setLevelDiscount(Float levelDiscount) {
        this.levelDiscount = levelDiscount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLevelName() {
        return levelName;
    }

    public Float getLevelDiscount() {
        return levelDiscount;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

}
