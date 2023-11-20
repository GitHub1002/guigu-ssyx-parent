/**
 * @Classname RabbitService
 * @Date 2023/11/14 15:01
 * @Created by Mingkai Feng
 * @Description TODO 对 RabbitMQ 发送消息的方法做封装
 */
package com.atguigu.ssyx.mq.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @author Mingkai Feng
     * @date 2023/11/14 15:11
     * @Description ToDo
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息内容
     * @return boolean
     */
    public boolean sendMessage (String exchange, String routingKey, Object message){
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        return true;
    }

    /**
     * @author Mingkai Feng
     * @date 2023/11/14 15:13
     * @Description ToDo
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息内容
     * @param delayTime 延迟时间
     * @return boolean
     */
    public boolean sendDelayMessage (String exchange, String routingKey, Object message, int delayTime){
        // 在发送消息的时候设置延迟时间
        rabbitTemplate.convertAndSend(exchange, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置一个延迟时间
                message.getMessageProperties().setDelay(delayTime * 1000);
                return message;
            }
        });
        return true;
    }
}
