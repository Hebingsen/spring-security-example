package com.sky.web.user.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.sky.web.user.pojo.User;

import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{

	List<User> findAll();

	//User findByPhone(String phone);

}
