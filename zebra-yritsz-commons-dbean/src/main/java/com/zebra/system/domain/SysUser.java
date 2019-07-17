package com.zebra.system.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.zebra.common.annotation.Excel;
import com.zebra.common.annotation.Excel.Type;
import com.zebra.common.annotation.Excels;
import com.zebra.common.core.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户对象 sys_user
 *
 * @author zebra
 */
@Getter
@Setter
public class SysUser extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	@Excel(name = "用户序号", prompt = "用户编号")
	private Long userId;

	/** 部门ID */
	@Excel(name = "部门编号", type = Type.IMPORT)
	private Long deptId;

	/** 部门父ID */
	private Long parentId;

	/** 角色ID */
	private Long roleId;

	/** 登录名称 */
	@Excel(name = "登录名称")
	private String loginName;

	/** 用户名称 */
	@Excel(name = "用户名称")
	private String userName;

	/** 用户邮箱 */
	@Excel(name = "用户邮箱")
	private String email;

	/** 手机号码 */
	@Excel(name = "手机号码")
	private String phonenumber;

	/** 用户性别 */
	@Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
	private String sex;

	/** 用户头像 */
	private String avatar;

	/** 密码 */
	private String password;

	/** 盐加密 */
	private String salt;

	/** 帐号状态（0正常 1停用） */
	@Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
	private String status;

	/** 删除标志（0代表存在 2代表删除） */
	private String delFlag;

	/** 最后登陆IP */
	@Excel(name = "最后登陆IP", type = Type.EXPORT)
	private String loginIp;

	/** 最后登陆时间 */
	@Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
	private Date loginDate;

	/** 部门对象 */
	@Excels({ @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
			@Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT) })
	private SysDept dept;

	private List<SysRole> roles;

	/** 角色组 */
	private Long[] roleIds;

	/** 岗位组 */
	private Long[] postIds;

	public boolean isAdmin() {
		return isAdmin(this.userId);
	}

	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("deptId", getDeptId()).append("loginName", getLoginName()).append("userName", getUserName())
				.append("email", getEmail()).append("phonenumber", getPhonenumber()).append("sex", getSex())
				.append("avatar", getAvatar()).append("password", getPassword()).append("salt", getSalt())
				.append("status", getStatus()).append("delFlag", getDelFlag()).append("loginIp", getLoginIp())
				.append("loginDate", getLoginDate()).append("createBy", getCreateBy())
				.append("createTime", getCreateTime()).append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).append("dept", getDept())
				.toString();
	}
}
