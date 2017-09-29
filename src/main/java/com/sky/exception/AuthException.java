package com.sky.exception;


import lombok.Data;

/**
 * 认证相关异常
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Data
public class AuthException extends RuntimeException {

	private static final long serialVersionUID = 17174741487766414L;

	private int code;
	private String message;

	public AuthException(String message) {
		super(message);
	}

	public AuthException(Throwable cause) {
		super(cause);
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthException(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
