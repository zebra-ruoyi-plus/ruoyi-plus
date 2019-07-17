package com.zebra.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zebra.common.annotation.Log;
import com.zebra.common.config.ConfigServerApplication;
import com.zebra.common.core.controller.BaseController;
import com.zebra.common.core.domain.AjaxResult;
import com.zebra.common.enums.BusinessType;
import com.zebra.common.utils.http.HttpUtil;
import com.zebra.framework.util.ShiroUtils;
import com.zebra.system.domain.SysMenu;
import com.zebra.system.domain.SysUser;
import com.zebra.system.service.ISysMenuService;

/**
 * 首页 业务处理
 *
 * @author ruoyi
 */
@Controller
public class SysIndexController extends BaseController {
	@Autowired
	private ISysMenuService menuService;
	@Autowired
	private ConfigServerApplication configServerApplication;

	// 系统首页
	@GetMapping("/index")
	public String index(ModelMap mmap) {
		// 取身份信息
		SysUser user = ShiroUtils.getSysUser();
		// 根据用户id取出菜单
		List<SysMenu> menus = menuService.selectMenusByUser(user);
		mmap.put("menus", menus);
		mmap.put("user", user);
		mmap.put("copyrightYear", configServerApplication.getCopyrightYear());
		mmap.put("demoEnabled", configServerApplication.getDemoEnabled());
		return "index";
	}

	// 系统介绍
	@GetMapping("/system/main")
	public String main(ModelMap mmap) {
		mmap.put("version", configServerApplication.getVersion());
		return "main";
	}

	// 同步配置中心
	@Log(title = "同步配置中心", businessType = BusinessType.SYNC)
	@PostMapping("/system/syncConfig")
	@ResponseBody
	public AjaxResult syncConfig() {
		HttpUtil httpUtil = HttpUtil.getInstance("utf-8", 20000, 20000);
		String reString = httpUtil.sendHttpPost(configServerApplication.getRefresh(), null, null, null,
				MimeTypeUtils.APPLICATION_JSON);
		logger.info("[信息]刷新配置信息返回：" + reString);
		return toAjax(1);
	}
}
