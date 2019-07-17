package com.zebra.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.zebra.common.annotation.Excel;
import com.zebra.common.core.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 字典类型表 sys_dict_type
 *
 * @author zebra
 */
@Getter
@Setter
public class SysDictType extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 字典主键 */
	@Excel(name = "字典主键")
	private Long dictId;

	/** 字典名称 */
	@Excel(name = "字典名称")
	private String dictName;

	/** 字典类型 */
	@Excel(name = "字典类型 ")
	private String dictType;

	/** 状态（0正常 1停用） */
	@Excel(name = "状态", readConverterExp = "0=正常,1=停用")
	private String status;

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("dictId", getDictId())
				.append("dictName", getDictName()).append("dictType", getDictType()).append("status", getStatus())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}
}
