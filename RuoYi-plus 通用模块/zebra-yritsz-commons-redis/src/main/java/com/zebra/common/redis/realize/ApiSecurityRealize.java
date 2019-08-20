package com.zebra.common.redis.realize;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zebra.bussiness.domain.ApiSecurity;
import com.zebra.bussiness.mapper.ApiSecurityMapper;
import com.zebra.common.redis.IBaseCache;
import com.zebra.common.redis.realize.base.RealizeBase;
import com.zebra.common.redis.realize.base.RealizeParentBase;

import lombok.extern.slf4j.Slf4j;

/**
 * API权限缓存实现
 *
 * @author zebra
 *
 */
@Repository(value = "apiSecurityRealize")
@Slf4j
public class ApiSecurityRealize extends RealizeParentBase implements RealizeBase<ApiSecurity> {
	public static final String SU_KEY = "api_security";

	@Autowired
	private ApiSecurityMapper apiSecurityMapper;

	@Autowired
	private IBaseCache redisCache;

	@Override
	public boolean init(String key) {
		try {
			this.del(super.getBasicKey(key, SU_KEY));
			List<ApiSecurity> apiSecurities = apiSecurityMapper.selectAll();
			for (ApiSecurity apiSecurity : apiSecurities) {
				redisCache.hPut(super.getBasicKey(key, SU_KEY), apiSecurity.getApiKey(), apiSecurity);
			}
			log.info("[信息]API权限信息初始化成功");
			return true;

		} catch (Exception e) {
			log.error("[信息]API权限信息初始化异常", e);
		}
		return false;
	}

	@Override
	public ApiSecurity getHk(String key, String field) {
		return redisCache.hGet(super.getBasicKey(key, SU_KEY), field);
	}

	@Override
	public void del(String key) {
		redisCache.del(super.getBasicKey(key, SU_KEY));
	}
}
