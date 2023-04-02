package com.chitchat.portal.dto.account;

import com.chitchat.common.base.BasePageRequestModel;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.chitchat.portal.enumerate.EnumAccountCenterType;
import com.chitchat.portal.enumerate.EnumUserFollowType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 粉丝列表、关注列表、朋友列表、访客列表
 *
 * Created by Js on 2022/8/7.
 **/
@Data
@Accessors(chain = true)
public class AccountCenterPageListRequestDTO extends BasePageRequestModel {
    //个人中心列表类型
    @ApiModelProperty(hidden = true)
    private Integer followType;
    @ApiModelProperty(hidden = true)
    private EnumUserFollowType enumUserFollowType;
    //目标id
    @ApiModelProperty(hidden = true)
    private Long targetId;
    //账号id
    @ApiModelProperty(hidden = true)
    private Long accountId;

    public void setFollowType(Integer followType) {
        this.followType = followType;
        this.enumUserFollowType = followType == null ? null : EnumUtil.valueOf(EnumUserFollowType.class, followType);
    }

    @ApiModelProperty(value = "列表类型(1-粉丝 2-关注 3-访客 4-朋友)", required = true, example = "1")
    @NotNull(message = "列表类型不能为空")
    @EnumValue(intValues = {1,2,3,4}, message = "列表类型参数有误")
    private Integer centerType;
    @ApiModelProperty(hidden = true)
    private EnumAccountCenterType enumAccountCenterType;

    public void setCenterType(Integer centerType) {
        this.centerType = centerType;
        this.enumAccountCenterType = centerType == null ? null : EnumUtil.valueOf(EnumAccountCenterType.class, centerType);
    }
}
