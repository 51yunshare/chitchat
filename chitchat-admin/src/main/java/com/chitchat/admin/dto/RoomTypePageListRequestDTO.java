package com.chitchat.admin.dto;

import com.chitchat.common.base.BasePageRequestModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 房间类型
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@ApiModel
@Accessors(chain = true)
public class RoomTypePageListRequestDTO extends BasePageRequestModel {

}
