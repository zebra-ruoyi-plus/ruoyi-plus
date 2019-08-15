package com.zebra.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zebra.common.utils.DateUtils;
import com.zebra.common.utils.StringUtils;
import com.zebra.system.domain.SysUserOnline;
import com.zebra.system.mapper.SysUserOnlineMapper;
import com.zebra.system.service.ISysUserOnlineService;

/**
 * 在线用户 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService {
	@Autowired
	private SysUserOnlineMapper userOnlineDao;

	/**
	 * 通过会话序号查询信息
	 * 
	 * @param sessionId
	 *            会话ID
	 * @return 在线用户信息
	 */
	@Override
	public SysUserOnline selectOnlineById(String sessionId) {
		return userOnlineDao.selectOnlineById(sessionId);
	}

	/**
	 * 通过会话序号删除信息
	 * 
	 * @param sessionId
	 *            会话ID
	 * @return 在线用户信息
	 */
	@Override
	public void deleteOnlineById(String sessionId) {
		SysUserOnline userOnline = selectOnlineById(sessionId);
		if (StringUtils.isNotNull(userOnline)) {
			userOnlineDao.deleteOnlineById(sessionId);
		}
	}

	/**
	 * 通过会话序号删除信息
	 * 
	 * @param sessions
	 *            会话ID集合
	 * @return 在线用户信息
	 */
	@Override
	public void batchDeleteOnline(List<String> sessions) {
		for (String sessionId : sessions) {
			SysUserOnline userOnline = selectOnlineById(sessionId);
			if (StringUtils.isNotNull(userOnline)) {
				userOnlineDao.deleteOnlineById(sessionId);
			}
		}
	}

	/**
	 * 保存会话信息
	 * 
	 * @param online
	 *            会话信息
	 */
	@Override
	public void saveOnline(SysUserOnline online) {
		SysUserOnline on = selectOnlineById(online.getSessionId());
		if (!StringUtils.isNotNull(on)) {
			userOnlineDao.saveOnline(online);
		} else {
			userOnlineDao.updateOnline(online);
		}
	}

	/**
	 * 查询会话集合
	 * 
	 * @param userOnline
	 *            在线用户
	 */
	@Override
	public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline) {
		return userOnlineDao.selectUserOnlineList(userOnline);
	}

	/**
	 * 强退用户
	 * 
	 * @param sessionId
	 *            会话ID
	 */
	@Override
	public void forceLogout(String sessionId) {
		userOnlineDao.deleteOnlineById(sessionId);
	}

	/**
	 * 查询会话集合
	 * 
	 * @param expiredDate
	 *            失效日期
	 */
	@Override
	public List<SysUserOnline> selectOnlineByExpired(Date expiredDate) {
		String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
		return userOnlineDao.selectOnlineByExpired(lastAccessTime);
	}
}
