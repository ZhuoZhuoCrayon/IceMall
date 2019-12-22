package com.crayon.dao;

import com.crayon.pojo.Transportation;

import java.util.List;

public interface TransportationDao {
    Integer countTransportation() throws Exception;
    List<Transportation> listAllTransportation() throws Exception;
    Transportation getTransportationByKey(Integer transId) throws Exception;
    void insert(Transportation transportation) throws Exception;
    void update(Transportation transportation) throws Exception;
    void deleteByKey(Integer transId) throws Exception;
}
