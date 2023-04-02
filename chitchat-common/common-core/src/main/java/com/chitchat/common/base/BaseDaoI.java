package com.chitchat.common.base;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Js on 2022/7/23
 */
public interface BaseDaoI<T> {

    /**
     * 删除指定ID记录
     *
     * @param id
     */
    int deleteById(@Param("id") Integer id);

    int deleteByLongId(@Param("id") Long id);

    /**
     * 删除指定ID记录
     *
     * @param ids
     */
    int deleteByIds(List<Object> ids);

    /**
     * 新增
     *
     * @param data
     */
    int insert(T data);

    /**
     * 修改所有值
     *
     * @param data
     * @return
     */
    int update(T data);

    /**
     * 修改字段不为空的值
     *
     * @param data
     * @return
     */
    int updateByPrimaryKeySelective(T data);

    /**
     * 查询
     *
     * @param data
     * @return
     */
    List<T> list(BasePageRequestModel data);


    T selectByPrimaryKey(Integer id);

    T getById(Long id);

    /**
     * 通过id搜索，返回json
     *
     * @param id
     * @return
     */
    Map<String, Object> selectMapByPrimaryKey(Integer id);
}
