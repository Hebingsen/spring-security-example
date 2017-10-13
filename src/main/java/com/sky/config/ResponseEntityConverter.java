package com.sky.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import com.sky.base.ResponseEntity;

/**
 * 响应类型转换
 * @作者 乐此不彼
 * @时间 2017年10月13日
 * @公司 sky工作室
 */
public class ResponseEntityConverter implements Converter<ResponseEntity, Map<String,Object>>{

	@Override
	public Map<String,Object> convert(ResponseEntity source) {
		Map<String,Object> target = new HashMap<String,Object>();
		target.put("msg", source.getMsg());
		target.put("code", source.getCode());
		if(source.getData() == null)
			target.put("data", source.getData());
		
		return target;
	}

}
