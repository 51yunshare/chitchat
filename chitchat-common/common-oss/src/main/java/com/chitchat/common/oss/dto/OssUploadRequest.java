package com.chitchat.common.oss.dto;

import com.chitchat.common.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Js 2022/8/30.
 */
@Data
@ApiModel("文件上传对象")
public class OssUploadRequest extends BaseRequest {
    @ApiModelProperty(value = "上传文件", required = true)
    @NotNull(message = "上传文件不能为空")
    private MultipartFile file;
    @ApiModelProperty(value = "上传文件全路径", required = true)
    @NotBlank(message = "上传文件路径不能为空")
    private String filePath;
}
