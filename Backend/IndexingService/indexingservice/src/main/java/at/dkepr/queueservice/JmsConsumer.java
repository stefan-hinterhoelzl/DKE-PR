package at.dkepr.queueservice;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import at.dkepr.entity.UserSearchEntity;


@Component
@EnableJms
public class JmsConsumer {
    private final Logger logger = LoggerFactory.getLogger(JmsConsumer.class);
    
    @JmsListener(destination = "user-search-queue", containerFactory = "jmsListenerContainerFactory")
	public void receive(UserSearchEntity message){
		logger.info(message.getEmail());
	}
}
