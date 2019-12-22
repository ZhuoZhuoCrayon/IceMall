package com.crayon.service;

import com.crayon.dto.Result;
import com.crayon.dto.TransBean;
import com.crayon.pojo.Transportation;

import java.util.HashMap;

public interface TransportationService extends BaseService<Transportation> {

    /**
     * 列举快递方式及价格
     * @return
     */
    HashMap<String,Float> listTransMethods();

    /**
     * 为订单分配管理员
     * @return
     */
    Integer allocEidForTrans();

    /**
     * 根据物流方式初始化物流信息
     * @return
     */
    Result initTransportation(TransBean transBean);

    /**
     * 确认送达
     * @param transId
     * @return
     */
    Result confirmArrive(Integer transId) throws Exception;
}
