package com.sky.exception;


import lombok.Data;

/**
 * 认证相关异常
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Data
public class UserAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 17174741487766414L;

	private int code;
	private String msg;

	public UserAuthenticationException(String message) {
		super(message);
	}

	public UserAuthenticationException(Throwable cause) {
		super(cause);
	}

	public UserAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAuthenticationException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
}
