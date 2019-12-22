package com.crayon.pojo;

import java.util.Date;

public class Transportation {
    private Integer transId;
    private Integer resEId;
    private String transMethod;
    private String origin;
    private String destination;
    private Float postage;          //运费
    private Date transCreationTime;

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public void setResEId(Integer resEId) {
        this.resEId = resEId;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setPostage(Float postage) {
        this.postage = postage;
    }

    public void setTransCreationTime(Date transCreationTime) {
        this.transCreationTime = transCreationTime;
    }

    public void setTransMethod(String transMethod) {
        this.transMethod = transMethod;
    }

    public Integer getTransId() {
        return transId;
    }

    public Integer getResEId() {
        return resEId;
    }

    public Date getTransCreationTime() {
        return transCreationTime;
    }

    public Float getPostage() {
        return postage;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }

    public String getTransMethod() {
        return transMethod;
    }
}

