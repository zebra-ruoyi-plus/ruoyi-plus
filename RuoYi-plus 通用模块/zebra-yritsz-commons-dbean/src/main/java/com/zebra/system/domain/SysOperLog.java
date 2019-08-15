package com.zebra.system.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.zebra.common.annotation.Excel;
import com.zebra.common.core.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 操作日志记录表 oper_log
 *
 * @author zebra
 */
@Getter
@Setter
public class SysOperLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 日志主键 */
	@Excel(name = "操作序号")
	private Long operId;

	/** 操作模块 */
	@Excel(name = "操作模块")
	private String title;

	/** 业务类型（0其它 1新增 2修改 3删除） */
	@Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
	private Integer businessType;

	/** 业务类型数组 */
	private Integer[] businessTypes;

	/** 请求方法 */
	@Excel(name = "请求方法")
	private String method;

	/** 操作类别（0其它 1后台用户 2手机端用户） */
	@Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
	private Integer operatorType;

	/** 操作人员 */
	@Excel(name = "操作人员")
	private String operName;

	/** 部门名称 */
	@Excel(name = "部门名称")
	private String deptName;

	/** 请求url */
	@Excel(name = "请求地址")
	private String operUrl;

	/** 操作地址 */
	@Excel(name = "操作地址")
	private String operIp;

	/** 操作地点 */
	@Excel(name = "操作地点")
	private String operLocation;

	/** 请求参数 */
	@Excel(name = "请求参数")
	private String operParam;

	/** 操作状态（0正常 1异常） */
	@Excel(name = "状态", readConverterExp = "0=正常,1=异常")
	private Integer status;

	/** 错误消息 */
	@Excel(name = "错误消息")
	private String errorMsg;

	/** 操作时间 */
	@Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date operTime;

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("operId", getOperId())
				.append("title", getTitle()).append("businessType", getBusinessType())
				.append("businessTypes", getBusinessTypes()).append("method", getMethod())
				.append("operatorType", getOperatorType()).append("operName", getOperName())
				.append("deptName", getDeptName()).append("operUrl", getOperUrl()).append("operIp", getOperIp())
				.append("operLocation", getOperLocation()).append("operParam", getOperParam())
				.append("status", getStatus()).append("errorMsg", getErrorMsg()).append("operTime", getOperTime())
				.toString();
	}
}
