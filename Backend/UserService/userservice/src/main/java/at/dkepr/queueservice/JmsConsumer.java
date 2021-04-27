package at.dkepr.queueservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
@EnableJms
public class JmsConsumer {
    private final Logger logger = LoggerFactory.getLogger(JmsConsumer.class);
    
    @JmsListener(destination = "indexing-queue", containerFactory = "jmsListenerContainerFactory")
	public void receive(Object message){
		logger.info(message.toString());
	}
}
