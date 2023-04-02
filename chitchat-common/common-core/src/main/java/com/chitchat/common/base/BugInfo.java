package com.chitchat.common.base;

import com.chitchat.common.enumerate.EnumSystemType;
import com.chitchat.common.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * bug信息的Bean
 * Created system
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BugInfo extends BaseBean {
    // Fields
    private static final long serialVersionUID = 1L;
    /**
     * 主键，自增类型
     */
    private Integer id;
    /*****自定义属性区域begin******/
    /**
     * 系统类别
     * 系统类别
     */
    private EnumSystemType enumSystemType;
    /*
    * 用于接收前台数据
    */
    private Integer systemType;
    /**
     * 操作用户
     * 操作用户
     */
    private String operaUser;
    /**
     * 错误标题
     * 错误标题
     */
    private String errorTitle;
    /**
     * 错误详情
     * 错误详情
     */
    private String errorDescription;
    /**
     * 请求参数
     * 请求参数
     */
    private String parameter;
    /**
     * 请求路径
     * 请求路径
     */
    private String requestUrl;
    /**
     * 用户IP
     * 用户IP
     */
    private String ip;
    /**
     * 创建时间
     * 创建时间
     */
    private Date createdTime;


   public void setEnumSystemType(EnumSystemType enumSystemType) {
      this.enumSystemType = enumSystemType;
      this.systemType = enumSystemType == null ? null : enumSystemType.getIndex();
   }
    public void setSystemType(Integer systemType) {
        this.enumSystemType = systemType == null ? null : EnumUtil.valueOf(EnumSystemType.class, systemType);
      this.systemType = systemType;
    }
}
