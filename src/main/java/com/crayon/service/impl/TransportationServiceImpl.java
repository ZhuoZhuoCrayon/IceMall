package com.crayon.service.impl;

import com.crayon.dao.TransportationDao;
import com.crayon.dao.user_manage.EmployeeDao;
import com.crayon.dto.Result;
import com.crayon.dto.TransBean;
import com.crayon.pojo.Transportation;
import com.crayon.pojo.user_manage.Employee;
import com.crayon.service.TransportationService;
import com.crayon.setting.constant.SystemConstant;
import com.crayon.setting.constant.TransportConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
@Service
public class TransportationServiceImpl implements TransportationService {

    @Autowired
    private TransportationDao transportationDao;
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public HashMap<String, Float> listTransMethods() {
        HashMap<String,Float> transMethods = new HashMap<>();
        for(int i=0;i<TransportConstant.TransMethods.length;i++){
            transMethods.put(TransportConstant.TransMethods[i],TransportConstant.postage[i]);
        }
        return transMethods;
    }

    @Override
    public Integer countDOs() {
        try {
            return transportationDao.countTransportation();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Transportation> listAllDOs() {
        try{
            return transportationDao.listAllTransportation();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Transportation> listDOsById(Integer DOId) {
        return null;
    }

    @Override
    public Transportation getDOByKey(Integer DOId) {
        try{
            return transportationDao.getTransportationByKey(DOId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据物流方式初始化物流信息
     * @return
     */
    @Override
    public Result initTransportation(TransBean transBean){
        try{
            HashMap<String,Float> transMethods = this.listTransMethods();
            String transMethod = transBean.getTransMethod().trim();
            if (transMethods.containsKey(transMethod) == false){
                return new Result(false,"运输方式不存在");
            }

            //创建订单
            Transportation transportation = new Transportation();
            transportation.setTransMethod(transMethod);
            transportation.setOrigin(TransportConstant.origin);
            transportation.setDestination(transBean.getDestination());
            transportation.setPostage(transMethods.get(transMethod));
            transportation.setTransCreationTime(new Date());

            transportationDao.insert(transportation);

            //返回transId
            HashMap<String,Object> plusParams = new HashMap<>();
            plusParams.put("transId",transportation.getTransId());
            return new Result(true,"初始化物流信息成功",plusParams);

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"初始化物流信息失败");
        }
    }

    @Override
    public Result insert(Transportation DO) {
        try{
            transportationDao.insert(DO);
            return new Result(true,"新增物流信息成功");
        }catch (Exception e){
            return new Result(false,"新增物流失败");
        }
    }

    @Override
    public Result update(Transportation DO) {
        try{
            transportationDao.update(DO);
            return new Result(true,"修改物流信息成功");
        }catch (Exception e){
            return new Result(false,"更新物流信息失败");
        }
    }

    @Override
    public Result deleteById(Integer DOId) {
        return null;
    }

    @Override
    public Result deleteByKey(Integer DOId) {
        try{
            transportationDao.deleteByKey(DOId);
            return new Result(true,"删除物流信息成功");
        }catch (Exception e){
            return new Result(false,"删除物流信息失败");
        }
    }

    /**
     * 为订单分配物流管理员
     * @return
     */
    public Integer allocEidForTrans(){
        try{
            //列举所有的物流管理员
            List<Employee> employeeList = employeeDao.listEmployeesByRole(SystemConstant.LOGISTICS_ADMIN);
            Random random = new Random();
            //随机分配物流管理
            int empIdx = random.nextInt(employeeList.size());
            return employeeList.get(empIdx).getUserId();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 确认送达，设置时间
     * @param transId
     * @return
     */
    public Result confirmArrive(Integer transId) throws Exception{
        Transportation transportation = transportationDao.getTransportationByKey(transId);
        //设置送达时间
        transportation.setTransArriveTime(new Date());
        transportationDao.update(transportation);
        return new Result(true,"更新订单为到达");
    }
}
