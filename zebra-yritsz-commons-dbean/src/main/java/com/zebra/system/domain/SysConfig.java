package com.zebra.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.zebra.common.annotation.Excel;
import com.zebra.common.core.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 参数配置表 sys_config
 *
 * @author zebra
 */
@Getter
@Setter
public class SysConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 参数主键 */
	@Excel(name = "参数主键")
	private Long configId;

	/** 参数名称 */
	@Excel(name = "参数名称")
	private String configName;

	/** 参数键名 */
	@Excel(name = "参数键名")
	private String configKey;

	/** 参数键值 */
	@Excel(name = "参数键值")
	private String configValue;

	/** 系统内置（Y是 N否） */
	@Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
	private String configType;

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("configId", getConfigId())
				.append("configName", getConfigName()).append("configKey", getConfigKey())
				.append("configValue", getConfigValue()).append("configType", getConfigType())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}
}
