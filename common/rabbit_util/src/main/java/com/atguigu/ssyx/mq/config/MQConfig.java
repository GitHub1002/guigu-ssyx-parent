/**
 * @Classname MQConfig
 * @Date 2023/11/14 15:15
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.mq.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public MessageConverter messageconverter(){
        return new Jackson2JsonMessageConverter();
    }
}
