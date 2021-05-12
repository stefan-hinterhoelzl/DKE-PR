package at.dkepr.jmsService;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import at.dkepr.entity.Post;
import at.dkepr.postingservice.PostingService;

@Component
@EnableJms
public class jmsConsumer {

    private final PostingService service;

    public jmsConsumer(PostingService service) {
        this.service = service;
    }


    @JmsListener(destination = "posting-add-topic", containerFactory = "jmsListenerContainerFactoryTopic")
	public void receiveposting (Post message) {
        System.out.println(message.getId());
		service.savePost(message);
	}
    
}
