package com.chitchat.portal.bean.base;

import com.chitchat.common.base.BaseBean;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 标签信息
 * Created by Js on 2022/8/15 .
 **/
@Data
@Accessors(chain = true)
public class TagInfo extends BaseBean {

    private Long id;
    //标签名称
    private String tagName;
}
