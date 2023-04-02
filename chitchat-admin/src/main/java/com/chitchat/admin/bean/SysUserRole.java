package com.chitchat.admin.bean;

import com.chitchat.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户和角色关联 sys_user_role
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SysUserRole extends BaseBean {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
