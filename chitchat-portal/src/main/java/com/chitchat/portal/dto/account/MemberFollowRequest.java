package com.chitchat.portal.dto.account;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumUserFollowType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 会员关注
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "会员关注对象", description = "会员关注")
public class MemberFollowRequest implements Serializable {


    @ApiModelProperty(value = "关注目标id")
    @NotNull(message = "关注id不能为空！")
    private Long targetId;

    @ApiModelProperty(value = "关注目标类型，1-用户 2-房间")
    @NotNull(message = "关注类型不能为空！")
    private Integer followType;

    @ApiModelProperty(hidden = true)
    @NotNull(message = "关注类型参数有误！")
    private EnumUserFollowType enumUserFollowType;

    public void setFollowType(Integer followType) {
        this.followType = followType;
        this.enumUserFollowType = followType == null ? null : EnumUtil.valueOf(EnumUserFollowType.class, followType);
    }
}
