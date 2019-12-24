package com.crayon.setting.constant;

public interface SystemConstant {
    String STORE_PATH = "F:";
    String OUT_STORE_PATH = "C:/Users/crayon/OneDrive/IceMall/target/IceMall";
    String PREVIEW_IMG_PATH = "/img/preview/";
    String DETAIL_IMG_PATH = "/img/detail/";


    String CUSTOMER  = "customer";
    String PRODUCT_ADMIN = "productAdmin";
    String CUSTOMER_ADMIN = "customerAdmin";
    String EMPLOYEE_ADMIN = "employeeAdmin";
    String LOGISTICS_ADMIN = "logisticsAdmin";
    String SALES_ADMIN = "SalesAdmin";
    String SUPER_ADMIN = "superAdmin";

    Float WorkloadOfTrans = 5.2F;
    Float WorkloadOfSales = 0.05F;
    Float WorkloadOfCus = 5.6F;
    Float WorkloadOfPro = 1.5F;
    Float WorkloadOfEmp = 25.5F;

    boolean BY_EMP = true;
    boolean BY_USER = false;
}
