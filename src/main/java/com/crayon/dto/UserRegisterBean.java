package com.crayon.dto;

import java.io.Serializable;
import java.util.Date;

public class UserRegisterBean implements Serializable {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private Date birthday;


    public UserRegisterBean(){}

    public UserRegisterBean(String userName,
                            String password,
                            String email,
                            String phoneNumber,
                            Date birthday){
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthday() {
        return birthday;
    }


    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
