package com.chitchat.admin.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 字典传输对象
 *
 * @author
 */
@Data
public class DictInfoVO implements Serializable {

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
