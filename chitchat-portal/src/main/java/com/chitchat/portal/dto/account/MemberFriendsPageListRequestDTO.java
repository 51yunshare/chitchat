package com.chitchat.portal.dto.account;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 朋友列表
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("朋友列表请求对象")
public class MemberFriendsPageListRequestDTO extends BasePageRequestModel {

    private Long accountId;
}
