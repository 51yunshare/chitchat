package com.chitchat.common.oss.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Oss Service接口
 */
public interface OssServiceI {

    /**
     * 上传地址文件
     *
     * @param filePath
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    String uploadByPath(String filePath, String fileName);

    /**
     * 字节数组上传
     *
     * @param file
     * @param fileName
     * @return
     */
    String uploadByte(byte[] file, String fileName);

    String upload(MultipartFile file, String filePath);

    /**
     * 生成临时访问的url地址
     *
     * @param key
     * @return
     */
    String preSignedUrlOneMinute(String key, String xOssProcess, String contentType);


    /**
     * 获取文件字节数组
     *
     * @param imgSrc
     * @param watermark
     * @return
     */
    byte[] getStream(String imgSrc, String watermark);

    /**
     * 获取文件字节码
     *
     * @param imgSrc
     * @return
     */
    byte[] getFileStream(String imgSrc);

    /**
     * style = "image/resize,l_400,m_mfit";
     *
     * @param imgSrc
     * @param style
     * @return
     */
    byte[] getFileStreamByStyle(String imgSrc, String style);


    /**
     * 对已上传Object添加或更改对象标签
     *
     * @param tags
     */
    void setObjectTag(Map<String, String> tags, String objectName);


    /**
     * 获取对象标签
     *
     * @param objectName
     * @return
     */
    Map<String, String> getObjectTagging(String objectName);

    /**
     * 获取完整图片地址
     *
     * @param goodsImg
     * @return
     */
    String getUrl(String goodsImg);

    /**
     * 上传文件
     *
     * @param targetFile
     * @param filePathByDate
     * @param addHost
     * @return
     */
    String uploadByFile(File targetFile, String filePathByDate, boolean addHost);

    /**
     * 上传目录
     *
     * @param file
     * @param fileName
     * @param addHost
     * @return
     */
    List<String> uploadDir(File file, String fileName, boolean addHost);

    /**
     * 获取文件流
     *
     * @param filePath
     * @param tempFilePath
     * @return
     */
    File getTempFile(String filePath, String tempFilePath);

    /**
     * 生成临时访问的url地址*12小时
     *
     * @param key
     * @param xOssProcess
     * @param contentType
     * @param minutes
     * @return
     */
    String preSignedUrl(String key, String xOssProcess,
                        String contentType, int minutes);

    /**
     * 下载文件到指定流
     *
     * @param response
     * @param fileName
     * @param watermark
     * @param xOssProcess
     */
    void downloadResponse(HttpServletResponse response, String fileName, String watermark, String xOssProcess);

    /**
     * 单个文件删除
     *
     * @param filePath
     */
    void doDeletedObject(String filePath);

    /**
     * 批量删除
     *
     * @param filePaths
     */
    void batchDelObject(List<String> filePaths);

    /**
     * 将路径下的文件打包zip
     *
     * @param sourcePath
     * @param targetPath
     */
    void packZipFile(String sourcePath, String targetPath);
}
