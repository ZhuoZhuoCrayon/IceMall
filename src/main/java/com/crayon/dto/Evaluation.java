package com.crayon.dto;

public class Evaluation {
    private Integer proId;
    private Integer level;
    private String desHead;
    private String desBody;

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public void setDesBody(String desBody) {
        this.desBody = desBody;
    }

    public void setDesHead(String desHead) {
        this.desHead = desHead;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getProId() {
        return proId;
    }

    public String getDesHead() {
        return desHead;
    }

    public String getDesBody() {
        return desBody;
    }

    public Integer getLevel() {
        return level;
    }
}
