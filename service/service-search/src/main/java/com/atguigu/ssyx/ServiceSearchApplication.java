package com.atguigu.ssyx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname com.atguigu.ssyx.ServiceSearchApplication
 * @Date 2023/11/10 11:06
 * @Created by Mingkai Feng
 * @Description TODO
 */

// 取消数据源自动配置,因为当前服务操作的是 Elasticsearch，不是数据库
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// Nacos 注册
@EnableDiscoveryClient
// 远程调用
@EnableFeignClients
public class ServiceSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSearchApplication.class, args);
    }
}
