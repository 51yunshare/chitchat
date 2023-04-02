package com.chitchat.portal.service.base.impl;

import com.chitchat.common.oss.OssKey;
import com.chitchat.common.oss.service.OssServiceI;
import com.chitchat.common.util.ServletUtils;
import com.chitchat.portal.service.base.FlagsServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * 礼物service实现类
 */
@Service
@Slf4j
public class FlagsServiceImpl implements FlagsServiceI {

    @Autowired
    private OssServiceI ossServiceI;

    /**
     * 国旗下载
     */
    @Override
    public void flagsDown() {
        HttpServletResponse response = ServletUtils.getResponse();
        final String flagsPath = OssKey.OssPath.FLAGS_ZIP.getPath() + "flags.zip";
        log.debug("国旗文件下载，IP：{}；access file：{}", ServletUtils.getRealIp(ServletUtils.getRequest()), flagsPath);
        ossServiceI.downloadResponse(response, flagsPath, null, null);
    }
}
