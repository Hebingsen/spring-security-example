package com.sky.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sky.user.pojo.User;

import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{

	List<User> findAll();

}
