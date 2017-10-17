package com.sky.exception;


import org.springframework.security.core.AuthenticationException;

import lombok.Data;

/**
 * 认证相关异常
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Data
public class AuthException extends AuthenticationException {

	private static final long serialVersionUID = 17174741487766414L;

	private int code;

	public AuthException(String message) {
		super(message);
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthException(int code,String msg) {
		super(msg);
		this.code = code;
	}
	
	
}
