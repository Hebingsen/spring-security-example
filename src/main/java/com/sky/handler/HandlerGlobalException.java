package com.sky.handler;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sky.exception.ServiceException;
import com.xiaoleilu.hutool.util.MapUtil;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class HandlerGlobalException {

	/**
	 * 处理默认异常信息
	 */
	@ExceptionHandler(value = Exception.class)
	public Object handlerDefaultException(HttpServletRequest request, Exception e) throws Exception {
		log.error("异常信息:{}", e.getMessage());
		return MapUtil.builder().put("code", 500).put("msg", e.getMessage()).map();
	}
	
	/**
	 * 处理业务异常
	 */
	@ExceptionHandler(value = ServiceException.class)
	public Object handlerServiceException(HttpServletRequest request, ServiceException e) throws Exception {
		log.error("异常信息:{},异常状态码:{}", e.getMessage(), e.getCode());
		return MapUtil.builder().put("code", e.getCode()).put("msg", e.getMessage()).map();
	}
	
}
