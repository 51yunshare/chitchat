package com.chitchat.portal.bean.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 账号访客信息
 */
@Data
@Accessors(chain = true)
public class AccountVisitorsInfo implements Serializable {

    @ApiModelProperty(value = "账号访客关联主键id")
    private Long id;

    @ApiModelProperty(value = "访客id")
    private Long visitorId;

    @ApiModelProperty(value = "账号id")
    private Long accountId;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    //昵称
    @ApiModelProperty("用户昵称")
    private String nickName;
    //头像
    @ApiModelProperty("用户icon")
    private String icon;
    //性别
    @ApiModelProperty("用户性别: 0-未知 1-男 2-女")
    private Integer gender;

    /**
     * 国家
     */
    @ApiModelProperty("用户国家")
    private String country;

}
