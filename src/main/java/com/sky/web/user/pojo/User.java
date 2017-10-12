package com.sky.web.user.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户表POJO
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
@Data
@Accessors(chain = true)
@ToString
public class User implements Serializable{

	private static final long serialVersionUID = 4035622613913351358L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	private String userName;
	
	private String password;
	
	private String phone;
	
	private Date createTime;
	

}
