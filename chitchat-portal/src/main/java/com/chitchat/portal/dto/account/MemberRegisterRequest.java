package com.chitchat.portal.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 会员注册
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "会员注册对象", description = "会员注册对象")
public class MemberRegisterRequest implements Serializable {

    @ApiModelProperty(value = "手机号", required = true, example = "手机号。。")
    @NotBlank(message = "请填写手机号！")
    private String mobile;

    @ApiModelProperty(value = "密码", required = true, example = "密码。。")
    @NotBlank(message = "请填写密码！")
    @Length(min = 6, max = 24, message = "密码长度为6到24位")
    private String password;

    @ApiModelProperty(value = "昵称")
    @Length(max = 20, message = "昵称最长20个字符")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private MultipartFile iconFile;
    @ApiModelProperty(hidden = true)
    private String icon;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
    private Integer gender;

    @ApiModelProperty(value = "生日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
}
