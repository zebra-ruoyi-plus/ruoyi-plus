package com.zebra.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色和菜单关联 sys_role_menu
 *
 * @author zebra
 */
@Getter
@Setter
public class SysRoleMenu
{
    /** 角色ID */
    private Long roleId;

    /** 菜单ID */
    private Long menuId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("menuId", getMenuId())
            .toString();
    }
}
