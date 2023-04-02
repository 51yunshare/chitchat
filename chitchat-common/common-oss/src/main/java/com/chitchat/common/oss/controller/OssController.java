package com.chitchat.common.oss.controller;

import cn.hutool.core.util.StrUtil;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.chitchat.common.oss.dto.OssUploadRequest;
import com.chitchat.common.oss.service.OssServiceI;
import com.chitchat.common.util.ServletUtils;
import com.chitchat.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Oss
 *
 * Created by Js  2022/8/29.
 */
@Api(tags = "Oss上传/下载")
@Controller
@RequestMapping("/oss")
@Slf4j
public class OssController extends BaseController {

    @Autowired
    private OssServiceI aliOssServiceI;

    @ApiOperation(value = "上传")
    @RequestMapping(value = "upload" , method = RequestMethod.POST)
    @ResponseBody
    public ResultTemplate upload(@Validated OssUploadRequest uploadRequest){
        log.info("上传文件：{}" , uploadRequest.getFilePath());
        aliOssServiceI.upload(uploadRequest.getFile(), uploadRequest.getFilePath());
        return success();
    }

    /**
     * 下载/访问
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "下载")
    @RequestMapping(value = "/p/**" , method = RequestMethod.GET)
    public String sourcesP(HttpServletRequest request){
        String fileName = StringUtil.getExtractPath(request);
        if (StrUtil.isBlank(fileName)){
            return null;
        }
        String ip = ServletUtils.getRealIp(request);
        log.debug("图片/文件访问，IP：{}；access file：{}", ip, fileName);
        //外网处理OSS
        String url = aliOssServiceI.preSignedUrl(fileName, request.getParameter("x-oss-process"), request.getParameter("response-content-type"), 12 * 60);
        return "redirect:" + url;
    }

    /**
     * 返回流
     *
     * @param request
     * @param waterMark
     * @param servletResponse
     * @return
     */
    @ApiOperation(value = "流下载")
    @RequestMapping(value = "/stream/**" , method = RequestMethod.GET)
    public void sourcesS(HttpServletRequest request , @RequestParam(value = "waterMark" ,required = false) String waterMark,
                         HttpServletResponse servletResponse){
        String fileName = StringUtil.getExtractPath(request);
        if (StrUtil.isBlank(fileName)){
            return ;
        }
        String ip = ServletUtils.getRealIp(request);
        log.debug("文件预览-流访问，IP：{}；access file：{}", ip, fileName);
        try {
            aliOssServiceI.downloadResponse(servletResponse, fileName, waterMark, request.getParameter("x-oss-process"));
        } catch (Exception e) {
            log.error("处理文件流失败，文件：{}；错误原因：{}",fileName, ExceptionUtils.getStackTrace(e));
        }
    }

}
