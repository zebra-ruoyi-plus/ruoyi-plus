package com.zebra.api.provider.util;

import org.springframework.stereotype.Repository;

import com.zebra.api.provider.bean.Json;
import com.zebra.api.provider.enums.ResultEnum;

/**
 * 响应工具类
 *
 * @author zebra
 *
 */
@Repository
public class ResultUtil {

	/**
	 * 响应成功有参
	 *
	 * @param object
	 * @return
	 */
	public Json returnSuccess(Object object) {
		Json json = new Json();
		json.setCode(ResultEnum.SUCCESS.getCode());
		json.setMsg(ResultEnum.SUCCESS.getMsg());
		json.setObj(object);
		return json;
	}

	/**
	 * 响应成功无参
	 *
	 * @return
	 */
	public Json returnSuccess() {
		Json json = new Json();
		json.setCode(ResultEnum.SUCCESS.getCode());
		json.setMsg(ResultEnum.SUCCESS.getMsg());
		return json;
	}

	/**
	 * 响应失败有参
	 *
	 * @param object
	 * @return
	 */
	public Json returnError(Object object) {
		Json json = new Json();
		json.setCode(ResultEnum.ERROR.getCode());
		json.setMsg(ResultEnum.ERROR.getMsg());
		json.setObj(object);
		return json;
	}

	/**
	 * 响应失败无参
	 *
	 * @return
	 */
	public Json returnError() {
		Json json = new Json();
		json.setCode(ResultEnum.ERROR.getCode());
		json.setMsg(ResultEnum.ERROR.getMsg());
		return json;
	}

	/**
	 * 响应其他有参
	 *
	 * @param code
	 * @param msg
	 * @param object
	 * @return
	 */
	public Json returnOther(String code, String msg, Object object) {
		Json json = new Json();
		json.setCode(code);
		json.setMsg(msg);
		json.setObj(object);
		return json;
	}

	/**
	 * 响应其他无参
	 *
	 * @param code
	 * @param msg
	 * @return
	 */
	public Json returnOther(String code, String msg) {
		Json json = new Json();
		json.setCode(code);
		json.setMsg(msg);
		return json;
	}

}
