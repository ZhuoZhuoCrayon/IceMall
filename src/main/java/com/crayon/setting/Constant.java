package com.crayon.setting;

/**
 * @author crayon
 * @describe 系统常量
 */
public interface Constant {
    /**
     * 产品区常量定义
     */
    int FULL_REDUCE = 1;        //满减优惠
    int PRICE_DISCOUNT = 2;     //价格打折
    int FULL_DISCOUNT = 3;      //满价打折

    String[] PREFERENCES = {"","FULL_REDUCE","PRICE_DISCOUNT","FULL_DISCOUNT"};

    String ON_SALE = "ON_SALE";     //在售
    String DOWN = "DOWN";           //下架

    /**
     * 订单区常量定义
     */
    int UNPAID = 1;
    int PAID  = 2;
    int PAID_TRANPOSTING = 3;
    int FINISHED = 4;
    int CANCEL = 5;
    String[] ORDER_STATUS = {"","UNPAID","PAID","PAID_TRANPOSTING","FINISHED"};

}
