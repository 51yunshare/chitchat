package com.chitchat.portal.dto.room;

import com.chitchat.common.validation.EnumValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * 新建房间
 *
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "新建房间请求对象", description = "新建房间对象")
public class RoomAddRequestDTO implements Serializable {

    @ApiModelProperty(value = "房间名称", required = true, example = "闲聊房间")
    @NotBlank(message = "请填写房间名称！")
    @Length(max = 30, message = "房间名称最大30个字符")
    private String roomName;

    @ApiModelProperty(value = "房间的公告")
    @Length(max = 100, message = "房间公告最大100个字符")
    private String roomNotice;

    @ApiModelProperty(value = "房间图片", dataType = "MultipartFile")
    private MultipartFile imgFile;
    @ApiModelProperty(hidden = true)
    private String roomImg;
    @ApiModelProperty(value = "房间类型(1-普通 2-付费...)", dataType = "int")
    private Integer roomType = 1;

    @ApiModelProperty(value = "房间语言")
    private String roomLanguage;

    @ApiModelProperty(value = "房间标签(数组)",dataType = "array")
    private Set<String> roomTag;

    @ApiModelProperty(value = "连麦方式：0-随便 1-邀请", dataType = "Integer")
    @EnumValue(intValues = {0, 1}, message = "连麦方式参数错误")
    private Integer micWay;
}
