package com.chitchat.portal.dto.room;

import com.chitchat.common.util.EnumUtil;
import com.chitchat.common.validation.EnumValue;
import com.chitchat.portal.enumerate.EnumGameType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建游戏
 * <p>
 * Created by Js on 2022/10/30 .
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "创建游戏-请求体")
public class RoomGameAddRequestDTO implements Serializable {
    private static final long serialVersionUID = 5215564253281310520L;

    @ApiModelProperty(value = "房间号", required = true, example = "258963")
    @NotNull(message = "The room number cannot be empty !")
    private String roomNo;

    @ApiModelProperty(value = "游戏类型(1-球赛轮盘 2-Slot 3-Ludo)", required = true, example = "1")
    @NotNull(message = "The game type cannot be empty !")
    @EnumValue(intValues = {1,2,3}, message = "Wrong game type parameters !")
    private Integer gameType;

    @ApiModelProperty(value = "游戏人数", required = true, example = "2")
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
