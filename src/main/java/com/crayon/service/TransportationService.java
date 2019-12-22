package com.crayon.service;

import com.crayon.pojo.Transportation;

import java.util.HashMap;

public interface TransportationService extends BaseService<Transportation> {
    HashMap<String,Float> listTransMethods();


}
