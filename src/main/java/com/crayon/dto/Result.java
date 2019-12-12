package com.crayon.dto;


import java.io.Serializable;

public class Result implements Serializable {
    private Boolean success;
    private String message;

    public Result(Boolean success,String message){
        this.success = success;
        this.message = message;
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
}
