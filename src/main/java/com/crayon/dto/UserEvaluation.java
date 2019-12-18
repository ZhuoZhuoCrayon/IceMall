package com.crayon.dto;

import java.util.Date;

public class UserEvaluation extends Evaluation{
    private String userName;
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


    public void setEvaDate(Date evaDate) {
        this.evaDate = evaDate;
    }

}
