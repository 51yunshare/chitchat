package com.chitchat.portal.vo.room;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.portal.enumerate.EnumGameType;
import com.chitchat.portal.vo.account.AccountBaseInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏房间-响应体
 *
 * Created by Js on 2022/8/17.
 **/
@Data
@ApiModel("游戏房间-响应体")
public class RoomGameInfoVO implements Serializable {

//    @ApiModelProperty(value = "房间id")
//    private Long id;

    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @ApiModelProperty(value = "游戏类型id")
    private Integer gameType;
    @ApiModelProperty(value = "游戏类型名称")
    private EnumGameType enumGameType;

    @ApiModelProperty(value = "游戏人数(玩游戏人数)")
    private Integer gameUserNum;

    @ApiModelProperty(value = "游戏上限人数(游戏人数+观众)")
    private Integer limitUserNum;

    @ApiModelProperty(value = "游戏在线人数(当前的游戏人数)")
    private Integer roomUserNum;

//    @ApiModelProperty(value = "游戏规则")
//    private JSONObject gameRules;

    public void setGameType(Integer gameType) {
        this.gameType = gameType;
        this.enumGameType = gameType == null ? null : EnumUtil.valueOf(EnumGameType.class, gameType);
    }
    //房间用户信息
    private List<AccountBaseInfoVO> accountList;
}
