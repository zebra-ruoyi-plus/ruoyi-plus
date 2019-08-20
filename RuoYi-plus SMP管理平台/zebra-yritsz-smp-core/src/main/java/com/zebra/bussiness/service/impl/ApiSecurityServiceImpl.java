package com.zebra.bussiness.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zebra.bussiness.domain.ApiSecurity;
import com.zebra.bussiness.mapper.ApiSecurityMapper;
import com.zebra.bussiness.service.IApiSecurityService;
import com.zebra.common.config.ConfigServerRedis;
import com.zebra.common.core.text.Convert;
import com.zebra.common.exception.CacheException;
import com.zebra.common.redis.realize.base.RealizeBase;

/**
 * API权限认证 服务层实现
 *
 * @author zebra
 * @date 2019-08-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiSecurityServiceImpl implements IApiSecurityService {
	@Autowired
	private ApiSecurityMapper apiSecurityMapper;

	@Autowired
	private RealizeBase<ApiSecurity> apiSecurityRealize;
	@Autowired
	private ConfigServerRedis configServerRedis;

	/**
	 * 查询API权限认证信息
	 *
	 * @param apiKey
	 *            API权限认证ID
	 * @return API权限认证信息
	 */
	@Override
	public ApiSecurity selectApiSecurityById(String apiKey) {
		return apiSecurityMapper.selectApiSecurityById(apiKey);
	}

	/**
	 * 查询API权限认证列表
	 *
	 * @param apiSecurity
	 *            API权限认证信息
	 * @return API权限认证集合
	 */
	@Override
	public List<ApiSecurity> selectApiSecurityList(ApiSecurity apiSecurity) {
		return apiSecurityMapper.selectApiSecurityList(apiSecurity);
	}

	/**
	 * 新增API权限认证
	 *
	 * @param apiSecurity
	 *            API权限认证信息
	 * @return 结果
	 * @throws Exception
	 */
	@Override
	public int insertApiSecurity(ApiSecurity apiSecurity) {
		apiSecurity.setApiCreateTime(new Date());
		apiSecurity.setApiUpdateTime(new Date());
		int i = apiSecurityMapper.insertApiSecurity(apiSecurity);
		if (i > 0) {
			boolean is_flus = apiSecurityRealize.init(configServerRedis.getKey());
			if (!is_flus) {
				throw new CacheException();
			}
		}
		return i;
	}

	/**
	 * 修改API权限认证
	 *
	 * @param apiSecurity
	 *            API权限认证信息
	 * @return 结果
	 */
	@Override
	public int updateApiSecurity(ApiSecurity apiSecurity) {
		apiSecurity.setApiUpdateTime(new Date());
		int i = apiSecurityMapper.updateApiSecurity(apiSecurity);
		if (i > 0) {
			boolean is_flus = apiSecurityRealize.init(configServerRedis.getKey());
			if (!is_flus) {
				throw new CacheException();
			}
		}
		return i;
	}

	/**
	 * 删除API权限认证对象
	 *
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteApiSecurityByIds(String ids) {

		int i = apiSecurityMapper.deleteApiSecurityByIds(Convert.toStrArray(ids));
		if (i > 0) {
			boolean is_flus = apiSecurityRealize.init(configServerRedis.getKey());
			if (!is_flus) {
				throw new CacheException();
			}
		}
		return i;
	}

}
