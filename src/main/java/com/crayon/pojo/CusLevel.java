package com.crayon.pojo;

import java.io.Serializable;

/**
 * 客户等级
 */
public class CusLevel implements Serializable {
    private Integer cusLevelId;
    private String levelName;
    private Float levelDiscount;


    public CusLevel(Integer cusLevelId,String levelName,Float levelDiscount){
        this.cusLevelId = cusLevelId;
        this.levelName = levelName;
        this.levelDiscount = levelDiscount;
    }
    public void setCusLevelId(Integer cusLevelId) {
        this.cusLevelId = cusLevelId;
    }

    public void setLevelDiscount(Float levelDiscount) {
        this.levelDiscount = levelDiscount;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getCusLevelId() {
        return cusLevelId;
    }

    public Float getLevelDiscount() {
        return levelDiscount;
    }

    public String getLevelName() {
        return levelName;
    }
}
