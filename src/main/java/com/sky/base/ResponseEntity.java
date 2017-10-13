package com.sky.base;

import com.google.gson.Gson;
import com.sky.exception.ServiceException;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * api web 响应结构:成功状态码值固定为1,失败状态码自己自定义
 * @作者 乐此不彼
 * @时间 2017年9月28日
 * @公司 sky工作室
 */
@Data
@Accessors(chain = true)
@ToString
public class ResponseEntity {

	private int code;
	private String msg;
	private Object data;

	public ResponseEntity(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ResponseEntity() {
		super();
	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	public static ResponseEntity success(String msg) {
		return new ResponseEntity(1, msg, null);
	}
	
	/**
	 * 成功响应
	 * @param msg
	 * @param data
	 * @return
	 */
	public static ResponseEntity success(String msg, Object data) {
		return new ResponseEntity(1, msg, data);
	}
	
	/**
	 * 失败响应
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ResponseEntity fail(int code, String msg) {
		return new ResponseEntity(code, msg, null);
	}
	
	/**
	 * 转化为json字符串格式(自动忽略为null的字段)
	 * @return
	 */
	public String toJson(){
		try {
			return new Gson().toJson(this);
		} catch (Exception e) {
			throw new ServiceException("json格式转换失败");
		}
	}

}
