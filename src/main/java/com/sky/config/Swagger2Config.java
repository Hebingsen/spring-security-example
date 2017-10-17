package com.sky.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author bingsen.he
 * @date 2017/10/15
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

	@Bean
	public Docket createRestApi() {
		/**
		 * 设置全局token请求头
		 */
		List<Parameter> list = new ArrayList<Parameter>();
		ParameterBuilder pb = new ParameterBuilder();
		pb.name("token").description("用户登录凭证").required(false).modelRef(new ModelRef("String")).parameterType("header");
		list.add(pb.build());

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.sky.web")).paths(PathSelectors.any()).build()
				.globalOperationParameters(list);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Swagger Api 在线接口文档").description("spring-security-example  ")
				.termsOfServiceUrl("980489022@qq.com").contact("bingsen.he").version("1.0").build();
	}

}
