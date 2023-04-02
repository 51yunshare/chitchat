package com.chitchat.common.base;

import com.chitchat.common.enumerate.EnumYesOrNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Js on 2022/7/23
 */
@Data
public class BaseBean implements Serializable {
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    /**
     * 最后修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
    /**
     * 修改次数
     */
    private Integer modifiedCount;

    /**
     * 备注
     */
    private String memo;

    /**
     * 删除状态
     */
    private EnumYesOrNo enumDeleted;
    private Integer deleted;
    /**
     * 创建人
     */
    private Long createdUserId;
    private String createdUserName;
}
