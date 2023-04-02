package com.chitchat.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Js on 2022/7/23
 */
@Data
@ApiModel
public class BasePageRequestModel extends BaseRequest implements Serializable {
    @ApiModelProperty(value = "页码", example = "1")
    protected Integer cp = 1;// 当前页
    @ApiModelProperty(value = "每页记录数", example = "10")
    protected Integer rows = 10;// 每页显示记录数
    @ApiModelProperty(hidden = true)
    protected String sort;// 排序字段
    @ApiModelProperty(hidden = true)
    protected String order = "asc";// asc/desc
    //搜索关键字
    @ApiModelProperty(value = "关键词搜素", example = "小小")
    private String likeName;
    //当前用户
    public BasePageRequestModel( ) {
    }


    public BasePageRequestModel(Integer cp, Integer rows) {
        this.cp = cp;
        this.rows = rows;
    }

    public void setCp(Integer cp) {
        if(cp != null)
            this.cp = cp;
    }
}
