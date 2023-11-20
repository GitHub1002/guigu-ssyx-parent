/**
 * @Classname MQProducerAckConfig
 * @Date 2023/11/14 15:59
 * @Created by Mingkai Feng
 * @Description TODO
 */
package com.atguigu.ssyx.mq.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MQProducerAckConfig implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {

    /**
     * @author Mingkai Feng
     * @date 2023/11/14 16:01
     * @Description ToDo 表示消息是否正确发送到了交换机上
     * @param correlationData 消息的载体
     * @param ack 判断是否发送到交换机上
     * @param cause 原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息发送成功");
        }
        else {
            System.out.println("消息发送失败！" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息主体：" + new String(message.getBody()));
        System.out.println("应答码：" + replyCode);
        System.out.println("描述：" + replyText);
        System.out.println("消息使用的交换器 exchange: " + exchange);
        System.out.println("消息使用的路由键 routing: " + routingKey);
    }
}
