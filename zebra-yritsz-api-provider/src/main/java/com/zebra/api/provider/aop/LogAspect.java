/**
 * @author am
 * 2018-07-02
 * 1.0.1
 */

package com.zebra.api.provider.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.zebra.api.provider.util.RequestUtil;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class LogAspect {

	@Pointcut("@annotation(com.zebra.api.provider.aop.LogAnnotation)")
	public void logAspect() {
	}

	@Before("logAspect()")
	public void doBefore(JoinPoint joinPoint) {
		String opertype = "";
		try {
			opertype = getlogAnnotationOper(joinPoint);
		} catch (Exception e) {
			log.error("[信息]通过反射获取注解描述失败", e);
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = RequestUtil.getIpAddr(request);
		log.info("<<<<<<【" + opertype + "】>>>>>");
		log.info("[信息]请求方法:"
				+ (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
		log.info("[信息]请求地址:" + request.getRequestURI() + "【" + ip + "】");
		log.info("[信息]请求参数:" + Arrays.toString(joinPoint.getArgs()));

	}

	@AfterReturning(pointcut = "logAspect()", returning = "j")
	public void doAfterReturning(JoinPoint joinPoint, Object j) {
		String result = "";
		if (j != null) {
			Object jsonObject = JSONObject.toJSON(j);
			result = jsonObject.toString();
			log.info("[信息]响应:" + result);
		}
		log.info("<<<<<<【结束】>>>>>");
	}

	@AfterThrowing(pointcut = "logAspect()", throwing = "ex")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
		log.info("[信息]异常:" + ex.getMessage());
		log.info("[信息]异常:" + ex.getStackTrace());
		StringBuffer sb = new StringBuffer("");
		for (int k = 0; k < ex.toString().length() + 10; k++) {
			sb.append("*");
		}
		log.info(sb.toString());
		log.info("*");
		log.info("*     日志定位：error_" + ex.hashCode());
		log.info("*     异常信息：" + ex.toString());
		log.info("*     相关位置:");
		for (int i = 0; i < ex.getStackTrace().length; i++) {
			StackTraceElement stackTraceElement = ex.getStackTrace()[i];// 得到异常棧的元素
			// 查看类文件，如果包名是com.temple开头的（com.temple可以自己定义）
			if (stackTraceElement.getClassName().startsWith("com.zebra")) {
				log.info("*" + stackTraceElement.toString());
			}
		}
		log.info("<<<<<<【结束】>>>>>");
	}

	/**
	 * 返回LogAnnotation 注解类中的oper
	 *
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getlogAnnotationOper(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Method[] methods = Class.forName(targetName).getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				if (method.getParameterTypes().length == method.getParameterTypes().length) {
					description = method.getAnnotation(LogAnnotation.class).oper().toString();
					break;
				}
			}
		}
		return description;
	}
}
