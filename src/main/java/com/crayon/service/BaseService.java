package com.crayon.service;

import com.crayon.dto.Result;

import java.util.List;

public interface BaseService<T> {
    /**
     * 获取表单
     * @return
     */
    List<T> findAll ();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    List<T> findById(Integer id);

    /**
     * 根据名称查找
     * @param name
     * @return
     */
    List<T> findByName(String name);

    /**
     * 插入数据
     * @param t
     */

    Result insert(T t);

    /**
     * 更新bug：如果撞id，会导致修改另外一行
     * 所以是需要两个参数的，原id和修改的元组
     */
    /**
     * 更新
     * @param t
     */
    Result update(T t);

    /**
     * 删除
     * @param id
     */
    Result delete(Integer id);

    /**
     * 格式检查
     * @param t
     * @return 格式错误信息
     */
    String checkFormat(T t);
}
