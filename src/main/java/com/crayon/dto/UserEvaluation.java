package com.crayon.dto;

import java.util.Date;

public class UserEvaluation extends Evaluation{
    private String userName;
    private String levelName;
    private Date evaDate;

    public void initByEvaluation(Evaluation evaluation){
        this.setDesBody(evaluation.getDesBody());
        this.setDesHead(evaluation.getDesHead());
        this.setLevel(evaluation.getLevel());
        this.setProId(evaluation.getProId());
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setEvaDate(Date evaDate) {
        this.evaDate = evaDate;
    }

    public Date getEvaDate() {
        return evaDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getLevelName() {
        return levelName;
    }
}
