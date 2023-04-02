package com.chitchat.portal.dto.base;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.chitchat.portal.enumerate.EnumReportType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户举报
 * <p>
 * Created by Js on 2022/7/31.
 **/
@Data
@ApiModel(value = "用户举报对象", description = "用户举报")
public class ReportInfoAddRequestDTO implements Serializable {

    @ApiModelProperty(value = "举报对象id", required = true)
    @NotNull(message = "举报对象id不能为空！")
    private Long targetId;
    private String targetName;

    @ApiModelProperty(value = "举报类型，1-用户 2-房间", required = true, allowableValues = "range[1,2]")
    @NotNull(message = "举报类型不能为空！")
    @EnumValue(intValues = {1, 2}, message = "举报类型参数有误！")
    private Integer reportType;
    private EnumReportType enumReportType;


    @ApiModelProperty(value = "举报理由", required = true, allowableValues = "1,2,3,4,5,6,7")
    @NotNull(message = "举报理由不能为空！")
    @EnumValue(intValues = {1, 2, 3, 4, 5, 6, 7}, message = "举报理由参数有误！")
    private Integer reason;

    @ApiModelProperty(value = "举报说明")
    private String memo;

    @ApiModelProperty(value = "截图附件")
    private String screenshot;

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
        this.enumReportType = reportType == null ? null : EnumUtil.valueOf(EnumReportType.class, reportType);
    }
}
