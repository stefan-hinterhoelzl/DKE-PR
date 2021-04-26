package at.dkepr.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class ConsumerConfig {
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;
  
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
      ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
      activeMQConnectionFactory.setBrokerURL(brokerUrl);
      return activeMQConnectionFactory;
    }
  
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
      DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
      factory.setConnectionFactory(activeMQConnectionFactory());
      factory.setConcurrency("1-3");
      return factory;
    }
  }