package com.chitchat.portal.dto.room;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.chitchat.portal.enumerate.EnumGameType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 加入房间
 *
 * Created by Js on 2022/8/7.
 **/
@Getter
@Setter
@ApiModel(value = "加入房间-请求体")
public class RoomGameRequestDTO implements Serializable {
    //游戏类型
    @ApiModelProperty(value = "游戏类型(1-球赛轮盘 2-Slot 3-Ludo)", required = true, example = "1")
    @NotNull(message = "The game type cannot be empty !")
    @EnumValue(intValues = {1,2,3}, message = "Wrong game type parameters !")
    private Integer gameType;
    //游戏人数
    @ApiModelProperty(value = "游戏人数(>=1)", required = true, example = "2")
    @NotNull(message = "The number of players cannot be empty !")
    @Min(value = 1,message = "The number of players must be greater than 1 !")
    private Integer gameUserNum;

    @ApiModelProperty(hidden = true)
    private EnumGameType enumGameType;

    public void setGameType(Integer gameType) {
        this.gameType = gameType;
        this.enumGameType = gameType == null ? null : EnumUtil.valueOf(EnumGameType.class, gameType);
    }
}
