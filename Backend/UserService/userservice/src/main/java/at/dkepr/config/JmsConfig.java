package at.dkepr.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.Queue;



@Configuration
public class JmsConfig {
    @Bean
    public Queue queue(){
        return new ActiveMQQueue("indexing-queue");
    }

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

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
      factory.setConcurrency("1-2");
      return factory;
    }
}