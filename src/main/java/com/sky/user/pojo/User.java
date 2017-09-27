package com.sky.user.pojo;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class User implements Serializable{

	private static final long serialVersionUID = 4035622613913351358L;
	
	protected Integer id;
	
	private String userName;
	
	private String password;
	
	private String phone;
	
	private Date createTime;
	
	//用户token
	@Transient
	private String access_token;

}
