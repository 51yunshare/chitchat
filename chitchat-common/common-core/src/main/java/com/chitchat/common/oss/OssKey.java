package com.chitchat.common.oss;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Oss KEY
 *
 * Created by Js on 2022/8/12 .
 **/
public final class OssKey {

    private static final String IMG = "img";

    /**
     * 图片存储路径
     *
     * @return
     */
    public static String getImgPath(String fileName){
        DateTime dateTime = DateUtil.date();
        return OssPath.IMG.getPath() + dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + IdUtil.randomUUID() + "." + FileUtil.getSuffix(fileName);
    }

    public static String getImgPath(String fileName, OssPath path){
        DateTime dateTime = DateUtil.date();
        return path.getPath() + dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + IdUtil.randomUUID() + "." + FileUtil.getSuffix(fileName);
    }

    /**
     * 礼物存储路径
     *
     * @param fileName
     * @param path
     * @param giftId
     * @return
     */
    public static String getGiftPath(String fileName, OssPath path, Long giftId){
        return path.getPath() + giftId + "." + FileUtil.getSuffix(fileName);
    }

    @Getter
    @AllArgsConstructor
    public enum OssPath{
        IMG(0, "img/"),
        GIFT(1, "gift/"),
        GIFT_EFFECT(2, "gift/effect/"),
        GIFT_EFFECT_ZIP(3, "gift/effect/zip/"),
        FLAGS_ZIP(4, "flags/zip/")

        ;

        private int index;
        private String path;

    }
}
