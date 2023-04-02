package com.chitchat.portal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 经验值配置
 *
 * Created by Js on 2022/12/16 .
 **/
@Data
@Configuration
@ConfigurationProperties(prefix="chitchat.level.exp")
@RefreshScope
public class LevelExpConfigProperties {

    /**
     * 房间时长经验值
     */
    private Integer roomDuration;
    /**
     * 房间时长经验值当天限额
     */
    private Integer roomDurationLimit;
    /**
     * 送礼经验值
     */
    private Integer giveGift;
    /**
     * 送礼经验值当天限额
     */
    private Integer giveGiftLimit;
    /**
     * 游戏
     */
    private Integer playGame;
    /**
     * 游戏经验当天限额
     */
    private Integer playGameLimit;

}
