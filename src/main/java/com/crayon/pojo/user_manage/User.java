package com.crayon.pojo.user_manage;

import com.crayon.dto.UserRegisterBean;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer userId;
    private String userName;
    private String password;
    private String salt;
    private String email;
    private String phoneNumber;
    private String address;
    private Date registerDate;
    private Date birthday;


    public User(){}

    public User(Integer userId,
                String userName,
                String password,
                String salt,
                String email,
                String phoneNumber,
                String address,
                Date registerDate,
                Date birthday){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.registerDate = registerDate;
        this.birthday = birthday;
    }


    /**
     * 将dto类转为User类，用于获取注册信息
     * @param uRb
     */
    public User(UserRegisterBean uRb){

        this.setUserName(uRb.getUserName());
        this.setPassword(uRb.getPassword());
        this.setEmail(uRb.getEmail());
        this.setPhoneNumber(uRb.getPhoneNumber());
        this.setBirthday(uRb.getBirthday());
        this.setAddress(uRb.getAddress());

        //auto create time
        this.setRegisterDate(new Date());

    }

    public void changeInfo(UserRegisterBean uRb){
        this.setUserName(uRb.getUserName());
        this.setEmail(uRb.getEmail());
        this.setPhoneNumber(uRb.getPhoneNumber());
        this.setBirthday(uRb.getBirthday());
        this.setAddress(uRb.getAddress());
        this.password = null;           //不允许修改密码
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getUserId() {
        return userId;
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getSalt() {
        return salt;
    }
}

