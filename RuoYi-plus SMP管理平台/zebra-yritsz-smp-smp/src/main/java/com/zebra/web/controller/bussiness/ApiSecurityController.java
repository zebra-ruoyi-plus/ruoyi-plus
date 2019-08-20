package com.zebra.web.controller.bussiness;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zebra.bussiness.domain.ApiSecurity;
import com.zebra.bussiness.service.IApiSecurityService;
import com.zebra.common.annotation.Log;
import com.zebra.common.core.controller.BaseController;
import com.zebra.common.core.domain.AjaxResult;
import com.zebra.common.core.page.TableDataInfo;
import com.zebra.common.enums.BusinessType;
import com.zebra.common.enums.ResultEnum;
import com.zebra.common.utils.poi.ExcelUtil;

/**
 * API权限认证 信息操作处理
 *
 * @author zebra
 * @date 2019-08-20
 */
@Controller
@RequestMapping("/bussiness/apiSecurity")
public class ApiSecurityController extends BaseController {
	private String prefix = "bussiness/apiSecurity";

	@Autowired
	private IApiSecurityService apiSecurityService;

	@RequiresPermissions("bussiness:apiSecurity:view")
	@GetMapping()
	public String apiSecurity() {
		return prefix + "/apiSecurity";
	}

	/**
	 * 查询API权限认证列表
	 */
	@RequiresPermissions("bussiness:apiSecurity:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ApiSecurity apiSecurity) {
		startPage();
		List<ApiSecurity> list = apiSecurityService.selectApiSecurityList(apiSecurity);
		return getDataTable(list);
	}

	/**
	 * 导出API权限认证列表
	 */
	@RequiresPermissions("bussiness:apiSecurity:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(ApiSecurity apiSecurity) {
		List<ApiSecurity> list = apiSecurityService.selectApiSecurityList(apiSecurity);
		ExcelUtil<ApiSecurity> util = new ExcelUtil<ApiSecurity>(ApiSecurity.class);
		return util.exportExcel(list, "apiSecurity");
	}

	/**
	 * 新增API权限认证
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存API权限认证
	 *
	 */
	@RequiresPermissions("bussiness:apiSecurity:add")
	@Log(title = "API权限认证", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ApiSecurity apiSecurity) {
		if (apiSecurityService.selectApiSecurityById(apiSecurity.getApiKey()) != null) {
			return error(ResultEnum.INFONOTNULL.getMsg());
		}
		return toAjax(apiSecurityService.insertApiSecurity(apiSecurity));
	}

	/**
	 * 修改API权限认证
	 */
	@GetMapping("/edit/{apiKey}")
	public String edit(@PathVariable("apiKey") String apiKey, ModelMap mmap) {
		ApiSecurity apiSecurity = apiSecurityService.selectApiSecurityById(apiKey);
		mmap.put("apiSecurity", apiSecurity);
		return prefix + "/edit";
	}

	/**
	 * 修改保存API权限认证
	 */
	@RequiresPermissions("bussiness:apiSecurity:edit")
	@Log(title = "API权限认证", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ApiSecurity apiSecurity) {
		ApiSecurity api = apiSecurityService.selectApiSecurityById(apiSecurity.getApiKey());
		if (api == null) {
			return error(ResultEnum.INFONULL.getMsg());
		}
		return toAjax(apiSecurityService.updateApiSecurity(apiSecurity));
	}

	/**
	 * 删除API权限认证
	 */
	@RequiresPermissions("bussiness:apiSecurity:remove")
	@Log(title = "API权限认证", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(apiSecurityService.deleteApiSecurityByIds(ids));
	}

}
