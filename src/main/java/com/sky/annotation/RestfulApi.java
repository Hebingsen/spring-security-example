package com.sky.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * 组合@RestController与@RequestMapping注解
 * @作者 乐此不彼
 * @时间 2017年10月12日
 * @公司 sky工作室
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@RequestMapping
@RestController
@Inherited
public @interface RestfulApi {
	
	@AliasFor("path")
	String[] value() default {};

	@AliasFor("value")
	String[] path() default {};
	
}
