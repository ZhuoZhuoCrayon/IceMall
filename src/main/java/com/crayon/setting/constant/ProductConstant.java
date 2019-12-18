package com.crayon.setting.constant;

public interface ProductConstant {
    /**
     * 产品区常量定义
     */
    int FULL_REDUCE = 1;        //满减优惠
    int PRICE_DISCOUNT = 2;     //价格打折
    int FULL_DISCOUNT = 3;      //满价打折

    String[] PREFERENCES = {"","FULL_REDUCE","PRICE_DISCOUNT","FULL_DISCOUNT"};
    String[] PREFERENCES_DESCIRBE = {"","满减优惠","价格折扣","满额折扣"};


    String ON_SALE = "ON_SALE";     //在售
    String DOWN = "DOWN";           //下架
}
