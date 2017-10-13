package com.sky.web.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.sky.web.user.pojo.Role;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
