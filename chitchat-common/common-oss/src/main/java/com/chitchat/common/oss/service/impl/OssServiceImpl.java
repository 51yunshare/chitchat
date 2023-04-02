package com.chitchat.common.oss.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.*;
import com.chitchat.common.base.CodeMsg;
import com.chitchat.common.exception.ChitchatException;
import com.chitchat.common.oss.config.OssConfig;
import com.chitchat.common.oss.service.OssServiceI;
import com.chitchat.common.util.FileDirUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Oss实现类
 * <p>
 * Created by Js on 2022/8/29 .
 **/
@Service
@Slf4j
public class OssServiceImpl implements OssServiceI {

    @Autowired
    private OSSClient ossClient;
    @Autowired
    private OssConfig aliOssConfig;

    @Override
    public String uploadByPath(String filePath, String fileName) {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            PutObjectResult putObjectResult = ossClient.putObject(aliOssConfig.getOssProperties().getBucketName(), fileName, inputStream);
            String url = getUrl(fileName);
            return url;
        } catch (FileNotFoundException e) {
            log.error("上传文件到oss失败：{}", ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("上传文件到oss失败：{}", ExceptionUtils.getStackTrace(e));
            }
        }
        // 关闭OSSClient。
//        ossClient.shutdown();
        return null;
    }

    @Override
    public String getUrl(String fileName) {
        String http = aliOssConfig.getOssProperties().getEndpoint().substring(0, aliOssConfig.getOssProperties().getEndpoint().indexOf("//") + 2);
        String addr = aliOssConfig.getOssProperties().getEndpoint().substring(aliOssConfig.getOssProperties().getEndpoint().indexOf("//") + 2);
        String url = http + aliOssConfig.getOssProperties().getBucketName() + "." + addr + "/" + fileName;
        return url;
    }

    @Override
    public String uploadByte(byte[] bytes, String fileName) {
        return upload(bytes, fileName, true);
    }

    @Override
    public String upload(MultipartFile file, String filePath) {
        try {
            return upload(file.getBytes(), filePath, false);
        } catch (IOException e) {
            log.error("上传文件到oss失败：{}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    private String upload(byte[] bytes, String fileName, boolean addHost) {
        long startTime = System.currentTimeMillis();
        try {
            OSSClient ossClient = aliOssConfig.getInnerOSSClient();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            ossClient.putObject(aliOssConfig.getOssProperties().getBucketName(), fileName, inputStream);
            inputStream.close();
//            ossClient.shutdown();
            log.info("上传成功：{}，耗时：{}", fileName, System.currentTimeMillis() - startTime);
        } catch (IOException e) {
            log.error("上传文件到oss失败：{}", ExceptionUtils.getStackTrace(e));
        }
        return addHost ? getUrl(fileName) : fileName;
    }

    @Override
    public String preSignedUrlOneMinute(String key, String xOssProcess, String contentType) {
        return preSignedUrl(key, xOssProcess, contentType, 1);
    }


    /**
     * 生成临时访问的url地址*12小时
     *
     * @param key
     * @param xOssProcess
     * @param contentType
     * @param minutes
     * @return
     */
    @Override
    public String preSignedUrl(String key, String xOssProcess, String contentType, int minutes) {
        // 创建OSSClient实例。
        // 设置URL过期时间为1分钟。
        Date expiration = new Date(new Date().getTime() + minutes * 60 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(aliOssConfig.getOssProperties().getBucketName(), key, HttpMethod.GET);
        req.setExpiration(expiration);

        req.setProcess(xOssProcess);

        if (StrUtil.isNotBlank(contentType)) {
            ResponseHeaderOverrides overrides = new ResponseHeaderOverrides();
            overrides.setContentType(contentType);
            req.setResponseHeaders(overrides);
        }

        URL url = ossClient.generatePresignedUrl(req);
        // 关闭OSSClient。
//        ossClient.shutdown();
        return url.toString();
    }

    /**
     * 下载文件到HttpServletResponse
     *
     * @param response
     * @param fileName
     * @param watermark
     * @param xOssProcess
     */
    @Override
    public void downloadResponse(HttpServletResponse response, String fileName, String watermark, String xOssProcess) {
        // 开始读取
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            OSSObject ossObject = getOSSObject(fileName, watermark, xOssProcess);
            inputStream = new BufferedInputStream(ossObject.getObjectContent());
            response.setContentLengthLong(ossObject.getObjectMetadata().getContentLength());
            response.setHeader("Accept-Ranges", "bytes");
            response.setContentType(ossObject.getObjectMetadata().getContentType());
            IoUtil.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("流转换文件失败，路径：{}，错误详情：{}", fileName, ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("流关闭失败，错误详情：{}", ExceptionUtils.getStackTrace(e));
            }
        }
    }

    @Override
    public String uploadByFile(File file, String filePath, boolean addHost) {
        OSSClient ossClient = null;
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }
        try {
            ossClient = aliOssConfig.getInnerOSSClient();
            ossClient.putObject(aliOssConfig.getOssProperties().getBucketName(), filePath, file);
        } catch (Exception e) {
            log.error("上传文件到oss失败：文件路径：{}，错误原因：{}，", filePath, ExceptionUtils.getStackTrace(e));
//            throw new ChitchatException(CodeMsg.OSS_ERROR, "上传到oss失败：" + e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return addHost ? getUrl(filePath) : filePath;
    }

    @Override
    public List<String> uploadDir(File file, String filePath, boolean addHost) {
        List result = new ArrayList<>();
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                log.info("图片路径：{}，名称：{}", filePath, listFile.getName());
                result.add(uploadByFile(listFile, filePath + "/" + listFile.getName(), addHost));
            }
        } else {
            result.add(uploadByFile(file, filePath, addHost));
        }
        return result;
    }

    /**
     * 获取文件流
     *
     * @param tempDownloadLocalFilePath
     * @return
     */
    @Override
    public File getTempFile(String filePath, String tempDownloadLocalFilePath) {
        OSSClient ossClient = aliOssConfig.getInnerOSSClient();
        File sourceFile = null;
        try {
            OSSObject ossObject = ossClient.getObject(aliOssConfig.getOssProperties().getBucketName(), filePath);
            sourceFile = new File(tempDownloadLocalFilePath);
            FileUtil.writeFromStream(ossObject.getObjectContent(), sourceFile);
            ossObject.close();
        } catch (Exception e) {
            log.error("文件不存在：{}\r\n错误详情：{}", filePath, ExceptionUtils.getStackTrace(e));
        } finally {
//            ossClient.shutdown();
        }
        return sourceFile;
    }


    /**
     * 获取文件字节流数组
     *
     * @param imgSrc
     * @param watermark
     * @return
     */
    @Override
    public byte[] getStream(String imgSrc, String watermark) {
        try {
            return IOUtils.readStreamAsByteArray(getInputStream(imgSrc, watermark));
        } catch (IOException e) {
            log.error("获取oss文件失败，文件路径：{}；错误：{}", imgSrc, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    private InputStream getInputStream(String imgSrc, String watermark) {
        return getInputStream(imgSrc, watermark, null);
    }

    private InputStream getInputStream(String imgSrc, String watermark, String xOssProcess) {
        OSSObject ossObject = getOSSObject(imgSrc, watermark, xOssProcess);
        return ossObject.getObjectContent();
    }

    private OSSObject getOSSObject(String imgSrc, String watermark, String xOssProcess) {
        //创建OSSClient实例
        //获取对象
        try {
            GetObjectRequest request = new GetObjectRequest(aliOssConfig.getOssProperties().getBucketName(), imgSrc);
            //图片水印目前仅支持JPG、PNG、BMP、WebP、TIFF格式
            /*if (EnumOssWatermarkImgType.checkWatermarkImg(imgSrc.substring(imgSrc.lastIndexOf(".") + 1))) {
                if (StrUtil.isBlank(watermark)) {
                    watermark = "image/watermark,image_d2F0ZXJtYXJrLnBuZz94LW9zcy1wcm9jZXNzPWltYWdlL3Jlc2l6ZSxQXzQw,g_center";
                }
            }*/
            if (StrUtil.isNotBlank(xOssProcess)) {
                watermark = StrUtil.isBlank(watermark) ? xOssProcess : xOssProcess + "," + watermark;
            }
            if (StrUtil.isNotBlank(watermark)) {
                request.setProcess(watermark);
            }
            OSSObject ossObject = ossClient.getObject(request);
            return ossObject;
        } catch (OSSException e) {
            log.error("获取oss文件失败，文件路径：{}；错误：{}", imgSrc, ExceptionUtils.getStackTrace(e));
            throw new ChitchatException(CodeMsg.OSS_ERROR, "处理失败：" + e.getErrorMessage());
        }
    }

    /**
     * 设置标签信息
     *
     * @param tags
     * @param objectName
     */
    @Override
    public void setObjectTag(Map<String, String> tags, String objectName) {

        // 在HTTP header中设置标签信息。
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setObjectTagging(tags);
        // 给文件设置标签。
        try {
            ossClient.setObjectTagging(aliOssConfig.getOssProperties().getBucketName(), objectName, tags);
        } catch (OSSException e) {
            e.printStackTrace();
            log.error(ExceptionUtils.getStackTrace(e));
        }

        // 关闭OSSClient。
//        ossClient.shutdown();
    }

    /**
     * 获取标签信息
     *
     * @param objectName
     * @return
     */
    @Override
    public Map<String, String> getObjectTagging(String objectName) {
        // 获取文件的标签。
        TagSet tagSet = ossClient.getObjectTagging(aliOssConfig.getOssProperties().getBucketName(), objectName);
        return tagSet.getAllTags();
    }

    /**
     * 获取文件字节流数组
     *
     * @param imgSrc
     * @return
     */
    @Override
    public byte[] getFileStream(String imgSrc) {
        if (StrUtil.isBlank(imgSrc)) {
            return null;
        }
        //创建OSSClient实例
        OSSClient ossClient = aliOssConfig.getInnerOSSClient();
        //获取对象
        try {
            OSSObject ossObject = ossClient.getObject(aliOssConfig.getOssProperties().getBucketName(), imgSrc);
            byte[] file = IOUtils.readStreamAsByteArray(ossObject.getObjectContent());
            ossObject.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件不存在：{}", imgSrc);
        }
//        ossClient.shutdown();
        return null;
    }

    /**
     * 获取文件字节流数组
     *
     * @param imgSrc
     * @return
     */
    @Override
    public byte[] getFileStreamByStyle(String imgSrc, String style) {
        //创建OSSClient实例
        OSSClient ossClient = aliOssConfig.getInnerOSSClient();
        //获取对象
        try {
            GetObjectRequest request = new GetObjectRequest(aliOssConfig.getOssProperties().getBucketName(), imgSrc);
            request.setProcess(style);
            OSSObject ossObject = ossClient.getObject(request);
            byte[] file = IOUtils.readStreamAsByteArray(ossObject.getObjectContent());
            ossObject.getObjectContent().close();
            ossObject.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件不存在：{}", imgSrc);
        }
//        ossClient.shutdown();
        return null;
    }

    /**
     * 单个文件删除
     *
     * @param dirName
     */
    @Override
    public void doDeletedObject(String dirName) {
        //创建OSSClient实例
        OSSClient ossClient = aliOssConfig.getInnerOSSClient();
        String bucketName = aliOssConfig.getOssProperties().getBucketName();
        try {
            // 删除文件。如果要删除目录，目录必须为空。
            ossClient.deleteObject(bucketName, dirName);
        } catch (Exception e) {
            log.error("oss删除文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 批量删除
     *
     * @param dirList
     */
    public void batchDelObject(List<String> dirList) {
        //创建OSSClient实例
        OSSClient ossClient = aliOssConfig.getInnerOSSClient();
        String bucketName = aliOssConfig.getOssProperties().getBucketName();
        // 删除文件。
        // 填写需要删除的多个文件完整路径。文件完整路径中不能包含Bucket名称。
        try {
            ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(dirList));
        } catch (Exception e) {
            log.error("oss删除文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 将路径下的文件打包zip
     *
     * @param sourcePath
     * @param targetPath
     */
    @Override
    public void packZipFile(String sourcePath, String targetPath) {
        //创建OSSClient实例
        OSSClient ossClient = aliOssConfig.getInnerOSSClient();
        String bucketName = aliOssConfig.getOssProperties().getBucketName();
        try {
            //获取所有文件路径
            List<String> allFilePath = this.findAllFilePath(sourcePath, ossClient, bucketName);
            //压缩所有的文件
            File zipFile = getZipFile(ossClient, bucketName, allFilePath);
            //上传zip包
            if (zipFile != null) {
                ossClient.putObject(bucketName, targetPath + "gift.zip", zipFile);
                FileUtil.del(zipFile);
            }
            log.info(">>>>>>>>>>>>>> 上传压缩包完成");
        }catch (Exception e){
            log.error("oss打包ZIP文件失败：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 压缩所有的文件
     *
     * @param ossClient
     * @param bucketName
     * @param allFilePath
     * @return
     */
    private File getZipFile(OSSClient ossClient, String bucketName, List<String> allFilePath) {
        File zipFile = null;
        if (ObjectUtil.length(allFilePath) > 0) {
            BufferedInputStream bis = null;
            try {
                zipFile = FileUtil.createTempFile("chitchat", ".zip", new File(FileDirUtil.getFileTempPath()), true);
                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
                JSONArray config = new JSONArray();
                for (String filePath : allFilePath) {
                    OSSObject ossObject = ossClient.getObject(bucketName, filePath);
                    if (ossObject != null){
                        byte[] buffs = new byte[1024 * 10];
                        String zFile = filePath.substring(filePath.lastIndexOf("/") + 1);
                        zos.putNextEntry(new ZipEntry(zFile));
                        bis = new BufferedInputStream(ossObject.getObjectContent(), 1024 * 10);

                        int read;
                        while ((read = bis.read(buffs, 0, 1024 * 10)) != -1) {
                            zos.write(buffs, 0, read);
                        }
                        ossObject.close();
                        zos.closeEntry(); // 当前文件写完，定位为写入下一条项目
                        config.add(new JSONObject().fluentPut("giftId", Integer.valueOf(FileUtil.getPrefix(zFile))).fluentPut("resource", zFile));
                    }
                }
                //添加配置文件
                File configFile = FileUtil.createTempFile("config", ".json", new File(FileDirUtil.getFileTempPath()), true);
                FileUtils.writeStringToFile(configFile, config.toJSONString());
                byte[] buffs = new byte[1024 * 10];
                zos.putNextEntry(new ZipEntry("config.json"));
                bis = new BufferedInputStream(new FileInputStream(configFile), 1024 * 10);
                int read;
                while ((read = bis.read(buffs, 0, 1024 * 10)) != -1) {
                    zos.write(buffs, 0, read);
                }
                zos.closeEntry();
                zos.close();
                FileUtil.del(configFile);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("文件添加zip文件失败：{}", ExceptionUtils.getStackTrace(e));
            }finally {
                //关闭流
                try {
                    if (null != bis) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return zipFile;
    }

    /**
     * 列举目录下所有文件
     *
     * @param path
     * @param ossClient
     * @param bucketName
     * @return
     */
    private List<String> findAllFilePath(String path, OSSClient ossClient, String bucketName) {
        // 指定前缀。若您希望遍历主目录文件夹，将该值置空。
        final String keyPrefix = path;
        ListObjectsV2Result result = null;
        String nextContinuationToken = null;
        //获取所有文件路径
        List<String> resultList = new ArrayList<>();
        do {
            // 默认情况下，每次列举100个文件或目录。
            ListObjectsV2Request request = new ListObjectsV2Request(bucketName)
                    .withDelimiter("/")
                    .withPrefix(keyPrefix);
            //接上一页
            request.setContinuationToken(StrUtil.isBlank(nextContinuationToken) ? null : nextContinuationToken);
            result = ossClient.listObjectsV2(request);
            //当前目录下所有目录
//            result.getCommonPrefixes();
            //当前目录下所有文件
            List<OSSObjectSummary> sums = result.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                String fileName = s.getKey().substring(s.getKey().lastIndexOf("/") + 1);
                if (StrUtil.isNotBlank(fileName)) {//隐藏文件夹本身文件
                    resultList.add(s.getKey());
                }
            }
            nextContinuationToken = result.getNextContinuationToken();
        } while (result.isTruncated());
        return resultList;
    }
}
