package com.sky.web.auth.request;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class RegisterReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 登录账号
	private String phone;

	// 登录密码
	private String password;

	// 用户名
	private String userName;

}
