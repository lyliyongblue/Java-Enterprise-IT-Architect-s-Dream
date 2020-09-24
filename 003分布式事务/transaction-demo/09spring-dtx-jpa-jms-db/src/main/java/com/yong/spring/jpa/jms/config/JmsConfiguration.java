package com.yong.spring.jpa.jms.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfiguration {

    @Value("${spring.activemq.broker-url:}")
    private String brokerUrl;

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerUrl);
        TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy(cf);
        proxy.setSynchedLocalTransactionAllowed(true);
        return proxy;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

}
