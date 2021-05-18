package at.dkepr.jmsService;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQTextMessage;
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


    @JmsListener(destination = "posting-add-couchbase", containerFactory = "jmsListenerContainerFactory")
	public void receiveposting (Post message) {
		service.savePost(message);
	}

    @JmsListener(destination = "posting-delete-couchbase", containerFactory = "jmsListenerContainerFactory")
	public void deleteposting (ActiveMQTextMessage message) {
        String text = "";
        try {
            text = message.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        
        if(text.contains("postid")) {
            service.deletebyId(text.substring(8, text.length()-1));
        }
        else{
            service.deleteAllbyAuthorId(text.substring(10, text.length()-1));
        }       
       
		
	}
    
}
