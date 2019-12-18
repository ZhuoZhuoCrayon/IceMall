package com.crayon.dto;


import java.io.Serializable;
import java.util.HashMap;

public class Result implements Serializable {
    private Boolean success;
    private String message;
    private HashMap<String,Object> plusParams;  //存储附加参数


    public Result(Boolean success,String message,HashMap<String,Object> plusParams){
        this.success = success;
        this.message = message;
        this.plusParams = plusParams;
    }
    public Result(Boolean success,String message){
        this.success = success;
        this.message = message;
    }

    public HashMap<String, Object> getPlusParams() {
        return plusParams;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPlusParams(HashMap<String, Object> plusParams) {
        this.plusParams = plusParams;
    }
}
