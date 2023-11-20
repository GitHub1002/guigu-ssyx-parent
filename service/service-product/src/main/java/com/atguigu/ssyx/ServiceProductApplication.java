/**
 * @Classname ServiceProductApplication
 * @Date 2023/11/3 18:09
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProductApplication.class, args);
    }
}
