package com.sky.web.user.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息响应dto
 * 
 * @作者 乐此不彼
 * @时间 2017年10月20日
 * @公司 sky工作室
 */
@Data
@Accessors(chain = true)
public class UserResp implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;

	protected String userName;

	protected String phone;

	// 最近一次登录时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date lastLoginTime;

}
