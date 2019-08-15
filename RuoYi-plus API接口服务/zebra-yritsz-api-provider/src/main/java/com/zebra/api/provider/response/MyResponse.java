package com.zebra.api.provider.response;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.zebra.api.provider.bean.Json;
import com.zebra.api.provider.enums.ResultEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求错误捕捉
 *
 * @author zebra
 *
 * @param <T>
 */
@RestControllerAdvice
@Slf4j
public class MyResponse<T> implements ResponseBodyAdvice<T> {
	@Override
	public T beforeBodyWrite(T t, MethodParameter arg1, MediaType arg2, Class<? extends HttpMessageConverter<?>> arg3,
			ServerHttpRequest arg4, ServerHttpResponse arg5) {
		return t;
	}

	@Override
	public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> arg1) {
		return true;
	}

	@ExceptionHandler(value = Exception.class)
	public Json defaultErrorHandler(Exception e, HttpServletResponse response) throws Exception {
		Json baseBeanResponse = new Json();
		if (e instanceof HttpRequestMethodNotSupportedException) {
			baseBeanResponse.setCode(ResultEnum.ERROR_400.getCode());
			baseBeanResponse.setMsg(e.getMessage());
			response.setStatus(400);
		} else if (e instanceof NoHandlerFoundException) {
			baseBeanResponse.setCode(ResultEnum.ERROR_400.getCode());
			baseBeanResponse.setMsg(e.getMessage());
			response.setStatus(404);
		} else if (e instanceof HttpMessageNotReadableException) {
			baseBeanResponse.setCode(ResultEnum.ERROR_404.getCode());
			baseBeanResponse.setMsg("The Request Body is not Validy");
			response.setStatus(400);
		} else {
			response.setStatus(500);
			baseBeanResponse.setCode(ResultEnum.ERROR_500.getCode());
			baseBeanResponse.setMsg(ResultEnum.ERROR_500.getMsg());
		}
		log.info("【信息】请求异常:" + e.getMessage());
		return baseBeanResponse;
	}
}
