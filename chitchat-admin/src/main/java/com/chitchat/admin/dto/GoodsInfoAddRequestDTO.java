package com.chitchat.admin.dto;

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
 * 新增商品
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "新增商品对象", description = "新增商品")
public class GoodsInfoAddRequestDTO implements Serializable {

    @ApiModelProperty(value = "商品类型", required = true)
    @NotNull(message = "商品类型不能为空！")
    private Long goodsCategoryId;

    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空！")
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    private MultipartFile cover;
    //商品价格
    @ApiModelProperty(value = "商品价格", required = true)
    @NotNull(message = "商品价格不能为空！")
    @DecimalMin(value = "0.01", message = "商品价格最小值0.01")
    private BigDecimal goodsPrice;
    /**
     * 商品促销价
     */
    @DecimalMin(value = "0.01", message = "商品价格最小值0.01")
    private BigDecimal promotionPrice;
    @ApiModelProperty(value = "商品动态效果")
    private MultipartFile goodsEffect;
    @ApiModelProperty(value = "商品有效期")
    private Integer duration;

    private Integer sort;
    //商品发布状态
    private Integer publishStatus;
    /**
     * 新品状态
     */
    private Integer newStatus;
    /**
     * 商品推荐状态
     */
    private Integer recommendStatus;
    /**
     * 成长值
     */
    private Integer giftGrowth;
    @ApiModelProperty(value = "商品描述")
    private String memo;
}
