package com.crayon.setting.constant;

public interface OrderConstant {
    /**
     * 订单区常量定义
     */
    int APPOINTMENT = 0;         //已预约
    int UNPAID = 1;              //待付款           清空购物车对应项，生成订单
    int PAID  = 2;               //待出库/已付款     用户点击付款，生成初始化物流信息
    int OUT_OF_STOCK = 3;        //正在出库 ：       库存减少
    int TRANSPORTING = 4;        //送货中           分配物流
    int DELIVERED = 5;           //已送达           可确认收货
    int FINISHED = 6;            //订单完成
    int CANCEL = 7;              //已取消           订单取消，库存加回
    String[] ORDER_STATUS = {"APPOINTMENT","UNPAID","PAID","OUT_OF_STOCK",
            "PAID_TRANSPORTING","DELIVERED","FINISHED","CANCEL"};
}
