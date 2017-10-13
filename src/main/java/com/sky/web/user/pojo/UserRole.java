package com.sky.web.user.pojo;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户角色关联表POJO
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
@Data
@ToString
@Accessors(chain = true)
@Table(name = "USER_ROLE")
public class UserRole implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private Long roleId;
	
}
