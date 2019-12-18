package com.crayon.pojo;

import java.io.Serializable;

public class Description implements Serializable {
    private Integer desId;
    private String type;
    private String category;
    private String desHead;
    private String desBody;

    public Description(){}
    public Description(Integer desId,
                       String type,
                       String category,
                       String desHead,
                       String desBody){
        this.desId = desId;
        this.type = type;
        this.category = category;
        this.desHead = desHead;
        this.desBody = desBody;
    }
    public void setDesId(Integer desId) {
        this.desId = desId;

    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDesHead(String desHead) {
        this.desHead = desHead;
    }

    public void setDesBody(String desBody) {
        this.desBody = desBody;
    }

    public Integer getDesId() {
        return desId;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getDesBody() {
        return desBody;
    }

    public String getDesHead() {
        return desHead;
    }
}
