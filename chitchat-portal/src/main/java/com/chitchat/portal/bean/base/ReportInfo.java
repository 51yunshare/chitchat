package com.chitchat.portal.bean.base;

import com.chitchat.common.base.BaseBean;
import lombok.Data;

import java.io.Serializable;

/**
 * 举报信息
 *
 * Created by Js on 2022/8/26 .
 */
@Data
public class ReportInfo extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    /**
     * 举报类型
     */
    private Integer reportType;

    /**
     * 房间/用户id
     */
    private Long targetId;
    private String targetName;

    /**
     * 用户id
     */
    private Long accountId;
    private String nickName;
    /**
     * 举报理由
     */
    private Integer reason;
    /**
     * 详细备注
     */
    private String memo;
    /**
     * 截图附件
     */
    private String screenshot;
}
