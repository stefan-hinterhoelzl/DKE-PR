package at.dkepr.queueservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import at.dkepr.entity.UserSearchEntity;

@Component
public class JmsProducer {
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Value("${jsa.activemq.queue}")
	String queue;
	
	public void send(UserSearchEntity user){
		jmsTemplate.convertAndSend(queue, user);
	}
}