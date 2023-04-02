package com.chitchat.portal.bean.base;

import com.chitchat.common.base.BaseBean;
import lombok.Data;

/**
 * 国旗信息
 *
 * Created by Js on 2022/8/27 .
 */
@Data
public class FlagsInfo extends BaseBean {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 国旗名称
     */
    private String flagName;

    /**
     * 国旗图标
     */
    private String flagUrl;
}
