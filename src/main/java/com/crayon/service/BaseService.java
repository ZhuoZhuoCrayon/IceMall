package com.crayon.service;

import com.crayon.dto.Result;

import java.util.List;

public interface BaseService<DO> {

    /**
     * 获取数据量
     * @return 数量
     * @throws Exception
     */
    Integer countDOs();

    /**
     * 获取所有的数据
     * @return
     * @throws Exception
     */
    List<DO> listAllDOs();

    /**
     * 根据Id查找
     * @param DOId
     * @return
     * @throws Exception
     */
    List<DO> listDOsById(Integer DOId);

    /**
     * 根据主码查找
     * @param DOId
     * @return
     * @throws Exception
     */
    DO getDOByKey(Integer DOId);

    /**
     * 插入并获得自增Id
     * @param DO
     * @throws Exception
     */
    Result insert(DO DO);

    /**
     * 更新
     * @param DO
     * @throws Exception
     */
    Result update(DO DO);

    /**
     * 根据Id删除
     * @param DOId
     * @throws Exception
     */
    Result deleteById(Integer DOId);

    /**
     * 根据主码删除
     * @param DOId
     * @throws Exception
     */
    Result deleteByKey(Integer DOId);
}
