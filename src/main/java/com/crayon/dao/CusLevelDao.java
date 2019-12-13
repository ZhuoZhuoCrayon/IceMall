package com.crayon.dao;

import com.crayon.pojo.CusLevel;

import java.util.List;

public interface CusLevelDao {
    List<CusLevel> findAll() throws Exception;
    List<CusLevel> findById(Integer id) throws Exception;

    /**
     * @param cusLevel
     * @return 返回自增id
     * @throws Exception
     */
    Integer insert(CusLevel cusLevel) throws Exception;
    void update(CusLevel cusLevel) throws Exception;
    void delete(Integer id) throws Exception;
}
