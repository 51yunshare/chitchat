package com.chitchat.common.base;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Js on 2022/7/23
 */
public interface BaseServicesI<T> {

    /**
     * 删除指定ID记录
     *
     * @param id
     */
    void deleteById(Integer id);

    void deleteByLongId(Long id);
    /**
     * 删除指定ID记录批量
     *
     * @param ids
     */
    void deleteByIds(List<Object> ids);

    /**
     * 新增
     *
     * @param object
     */
    int insert(T object);

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
     * 分页查询
     *
     * @param basePageRequestModel
     * @return
     */
    PageInfo list(BasePageRequestModel basePageRequestModel);

    T selectByPrimaryKey(Integer id);

    /**
     * 查询是否存在，不存在，抛异常
     *
     * @param id
     * @param errMsg
     * @return
     */
    T selectByPrimaryKeyThrow(Integer id, String errMsg);

    T getById(Long id, String errMsg);

    T getById(Long id);

    /**
     * 通过id搜索，返回json
     *
     * @param id
     * @param errMsg
     * @return
     */
    Map<String, Object> selectMapByPrimaryKeyThrow(Integer id, String errMsg);
}
