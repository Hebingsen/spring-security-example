package com.sky;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 网关中心
 * @author 乐此不彼
 * @date   2017-12-16
 */
@Slf4j
@EnableZuulProxy
@EnableFeignClients
@SpringCloudApplication
public class GateWayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GateWayApplication.class, args);
        String port = context.getEnvironment().getProperty("server.port");
        String desc = context.getEnvironment().getProperty("spring.application.desc");
        log.info(desc + "服务启动成功,端口号为:" + port);
    }
}

