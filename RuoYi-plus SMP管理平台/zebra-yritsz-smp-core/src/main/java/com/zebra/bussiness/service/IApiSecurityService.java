package com.zebra.bussiness.service;

import java.util.List;

import com.zebra.bussiness.domain.ApiSecurity;

/**
 * API权限认证 服务层
 *
 * @author zebra
 * @date 2019-08-20
 */
public interface IApiSecurityService
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
     * 删除API权限认证信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteApiSecurityByIds(String ids);

}
