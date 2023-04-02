package com.chitchat.portal.bean.room;

import com.chitchat.common.base.BaseBean;
import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumGameType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomGameInfo extends BaseBean {

    private static final long serialVersionUID = -2602581923551785406L;
    private Long id;

    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @ApiModelProperty(value = "游戏类型id")
    private Integer gameType;
    @ApiModelProperty(value = "游戏类型")
    private EnumGameType enumGameType;

    @ApiModelProperty(value = "游戏人数(玩游戏人数)")
    private Integer gameUserNum;

    @ApiModelProperty(value = "游戏上限人数(游戏人数+观众)")
    private Integer limitUserNum;

    @ApiModelProperty(value = "游戏在线人数(当前的游戏人数)")
    private Integer roomUserNum;

    @ApiModelProperty(value = "房间销毁时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date destroyTime;

    @ApiModelProperty(value = "创建人id/名称")
    private String creator;

    @ApiModelProperty(value = "游戏规则")
    private String gameRules;

    public void setGameType(Integer gameType) {
        this.gameType = gameType;
        this.enumGameType = gameType == null ? null : EnumUtil.valueOf(EnumGameType.class, gameType);
    }
}
