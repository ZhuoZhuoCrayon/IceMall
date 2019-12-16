package com.crayon.dao;

import com.crayon.pojo.CusLevel;

import java.util.List;

public interface CusLevelDao {

    Integer countCusLevels() throws Exception;
    List<CusLevel> listAllCusLevels() throws Exception;
    List<CusLevel> listCusLevelsById(Integer cusLevelId) throws Exception;
    CusLevel getCusLevelByKey(Integer cusLevelId) throws Exception;
    void insert(CusLevel cusLevel) throws Exception;
    void update(CusLevel cusLevel) throws Exception;
    void deleteById(Integer cusLevelId) throws Exception;
    void deleteByKey(Integer cusLevelId) throws Exception;
}
