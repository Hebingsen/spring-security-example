package com.sky.web.user.pojo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 角色表POJO
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
@Data
@ToString
@Accessors(chain = true)
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String roleName;
	private String roleCode;
}
