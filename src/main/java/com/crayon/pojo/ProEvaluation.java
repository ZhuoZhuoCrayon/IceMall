package com.crayon.pojo;

import java.util.Date;

public class ProEvaluation {
    private Integer proEvaId;
    private Integer userId;
    private Integer proId;
    private Integer level;
    private Date evaDate;
    private Integer desId;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setEvaDate(Date evaDate) {
        this.evaDate = evaDate;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public void setDesId(Integer desId) {
        this.desId = desId;
    }

    public void setProEvaId(Integer proEvaId) {
        this.proEvaId = proEvaId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getProId() {
        return proId;
    }

    public Integer getDesId() {
        return desId;
    }

    public Date getEvaDate() {
        return evaDate;
    }

    public Integer getProEvaId() {
        return proEvaId;
    }
}
