package com.chitchat.portal.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新增礼物
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "新增礼物对象", description = "新增礼物")
public class GiftAddRequestDTO implements Serializable {

    @ApiModelProperty(value = "礼物类型", required = true)
    @NotNull(message = "礼物类型不能为空！")
    private Long giftType;

    @ApiModelProperty(value = "礼物名称", required = true)
    @NotBlank(message = "礼物名称不能为空！")
    private String giftName;

    @ApiModelProperty(value = "礼物图标")
    private MultipartFile icon;
    //礼物价格
    @ApiModelProperty(value = "礼物价格", required = true)
    @NotNull(message = "礼物价格不能为空！")
    @DecimalMin(value = "0.01", message = "礼物价格最小值0.01")
    private BigDecimal giftPrice;
    @ApiModelProperty(value = "礼物动态效果")
    private MultipartFile giftEffect;

    private String memo;
}
