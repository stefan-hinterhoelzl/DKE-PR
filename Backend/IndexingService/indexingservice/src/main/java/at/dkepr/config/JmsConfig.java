package at.dkepr.config;


import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class JmsConfig {
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue("indexing-queue");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
      ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
      activeMQConnectionFactory.setBrokerURL(brokerUrl);
      System.out.println(brokerUrl);
      return activeMQConnectionFactory;
    }
  
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
      DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
      factory.setConnectionFactory(activeMQConnectionFactory());
      factory.setConcurrency("3-5");
      return factory;
    }
  }