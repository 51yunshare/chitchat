package com.chitchat.common.base;

import com.chitchat.common.exception.ChitchatException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Js on 2022/7/23
 */
public abstract class BaseServicesImpl<T, K extends BaseDaoI<T>> implements BaseServicesI<T> {
    @Autowired
    protected K baseDaoT;

    @Override
    @Transactional
    public void deleteById(Integer id) {
        baseDaoT.deleteById(id);
    }

    @Override
    public void deleteByLongId(Long id) {
        baseDaoT.deleteByLongId(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Object> id) {
        baseDaoT.deleteByIds(id);
    }

    @Override
    @Transactional
    public int insert(T object) {
        return baseDaoT.insert(object);
    }

    @Override
    @Transactional
    public int update(T data) {
        return baseDaoT.update(data);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(T data) {
        return baseDaoT.updateByPrimaryKeySelective(data);
    }

    @Override
    public PageInfo list(BasePageRequestModel basePageRequestModel) {
        PageHelper.startPage(basePageRequestModel.getCp(), basePageRequestModel.getRows());
        return new PageInfo(baseDaoT.list(basePageRequestModel));
    }

    @Override
    public T selectByPrimaryKey(Integer id) {
        return baseDaoT.selectByPrimaryKey(id);
    }

    @Override
    public T selectByPrimaryKeyThrow(Integer id, String errMsg) {
        T object = id == null ? null : baseDaoT.selectByPrimaryKey(id);
        if (object == null) {
            throw new ChitchatException(CodeMsg.NULL_ERROR, errMsg + "不存在");
        }
        return object;
    }
    @Override
    public T getById(Long id, String errMsg) {
        T object = id == null ? null : baseDaoT.getById(id);
        if (object == null) {
            throw new ChitchatException(CodeMsg.NULL_ERROR, errMsg + "不存在");
        }
        return object;
    }

    @Override
    public T getById(Long id) {
        return baseDaoT.getById(id);
    }

    @Override
    public Map<String, Object> selectMapByPrimaryKeyThrow(Integer id, String errMsg) {
        Map<String, Object> object = id == null ? null : baseDaoT.selectMapByPrimaryKey(id);
        if (object == null) {
            throw new ChitchatException(CodeMsg.NULL_ERROR, errMsg + "不存在");
        }
        return object;
    }
}
