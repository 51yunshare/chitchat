package com.chitchat.admin.bean;

import com.chitchat.common.base.BaseBean;
import lombok.Data;

/**
 * 字典
 *
 * @author 
 */
@Data
public class DictInfo extends BaseBean {

    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 字典类别
     */
    private Integer dictType;

    /**
     * 值
     */
    private String dictValue;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 备注说明
     */
    private String memo;
}