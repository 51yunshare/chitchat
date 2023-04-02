package com.chitchat.portal.vo.account;

import com.alibaba.fastjson.annotation.JSONField;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumGameType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Js on 2022/7/31.
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "会员信息-返回体")
public class AccountDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("背景图")
    private String bgImg;

    @ApiModelProperty("用户头像")
    private String icon;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
    private Integer gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生年月")
    private Date birthday;

    /**
     * 国家
     */
    @ApiModelProperty(value = "所在国家")
    private String country;

    @ApiModelProperty(value = "个人签名")
    private String personalizedSignature;

    @ApiModelProperty(value = "用户等级")
    private Long accountLevelId;
    /**
     * 游戏
     */
    @ApiModelProperty(value = "游戏等级")
    private Long accountGameLevelId;
    /**
     * VIP等级
     */
    @ApiModelProperty(value = "VIP等级")
    private Long vipLevelId;
    /**
     * 贵族等级
     */
    @ApiModelProperty(value = "贵族等级")
    private Long kingLevelId;
    /**
     * 我的关注数量
     */
    @ApiModelProperty(value = "我的关注数量")
    private Integer followingNum;
    /**
     * 关注我的数量
     */
    @ApiModelProperty(value = "关注我的数量")
    private Integer followerNum;

    @ApiModelProperty(value = "访客数量")
    private Integer visitors;

    @ApiModelProperty(value = "朋友数量")
    private Integer friends;

    @ApiModelProperty(value = "是否互关")
    private boolean follow;

    @ApiModelProperty(value = "最近玩的游戏")
    private List<AccountRecentGameVO> recentGameList;

    @ApiModelProperty(value = "收到所有礼物")
    private List<ReceivedGiftInfo> receivedGiftInfoList;

    @Data
    @ApiModel(value = "用户最近玩的游戏")
    public static class AccountRecentGameVO {
        @ApiModelProperty(value = "游戏类型id")
        private Integer gameType;
        @ApiModelProperty(value = "游戏类型")
        private EnumGameType enumGameType;

        public void setGameType(Integer gameType) {
            this.gameType = gameType;
            this.enumGameType = gameType == null ? null : EnumUtil.valueOf(EnumGameType.class, gameType);
        }
    }

    @Data
    @ApiModel(value = "收到礼物信息")
    public static class ReceivedGiftInfo {

        @ApiModelProperty(value = "礼物Id")
        private Long giftId;
        @ApiModelProperty(value = "礼物名称")
        private String giftName;
        @ApiModelProperty(value = "礼物图标")
        private String giftIcon;
        @ApiModelProperty(value = "礼物效果图")
        private String giftEffectUrl;
        @ApiModelProperty(value = "收到礼物总数")
        private Integer giftSumNum;
    }
}
