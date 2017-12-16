package com.sky.config;

import com.sky.base.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
//@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * 视图配置
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/swagger-ui.html");

	}

	/**
	 * 跨域配置
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
				.allowedMethods(Constant.SUPPORT_METHODS)
				.allowCredentials(false).maxAge(3600);
	}

}
