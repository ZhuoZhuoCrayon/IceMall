package com.crayon.setting.constant;

public interface OrderConstant {
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
