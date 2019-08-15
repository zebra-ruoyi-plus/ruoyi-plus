package com.zebra.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色和部门关联 sys_role_dept
 *
 * @author zebra
 */
@Getter
@Setter
public class SysRoleDept
{
    /** 角色ID */
    private Long roleId;

    /** 部门ID */
    private Long deptId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("deptId", getDeptId())
            .toString();
    }
}
