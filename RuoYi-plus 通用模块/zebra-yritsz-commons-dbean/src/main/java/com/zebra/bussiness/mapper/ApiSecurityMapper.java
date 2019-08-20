package com.zebra.bussiness.mapper;

import com.zebra.bussiness.domain.ApiSecurity;
import java.util.List;	
import tk.mybatis.mapper.common.Mapper;

/**
 * API权限认证 数据层
 * 
 * @author zebra
 * @date 2019-08-20
 */
public interface ApiSecurityMapper extends Mapper<ApiSecurity>
{
	/**
     * 查询API权限认证信息
     * 
     * @param apiKey API权限认证ID
     * @return API权限认证信息
     */
	public ApiSecurity selectApiSecurityById(String apiKey);
	
	/**
     * 查询API权限认证列表
     * 
     * @param apiSecurity API权限认证信息
     * @return API权限认证集合
     */
	public List<ApiSecurity> selectApiSecurityList(ApiSecurity apiSecurity);
	
	/**
     * 新增API权限认证
     * 
     * @param apiSecurity API权限认证信息
     * @return 结果
     */
	public int insertApiSecurity(ApiSecurity apiSecurity);
	
	/**
     * 修改API权限认证
     * 
     * @param apiSecurity API权限认证信息
     * @return 结果
     */
	public int updateApiSecurity(ApiSecurity apiSecurity);
	
	/**
     * 删除API权限认证
     * 
     * @param apiKey API权限认证ID
     * @return 结果
     */
	public int deleteApiSecurityById(String apiKey);
	
	/**
     * 批量删除API权限认证
     * 
     * @param apiKeys 需要删除的数据ID
     * @return 结果
     */
	public int deleteApiSecurityByIds(String[] apiKeys);
	
}