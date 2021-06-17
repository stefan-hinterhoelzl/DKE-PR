package at.dkepr.queueservice;



import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import at.dkepr.entity.Post;
import at.dkepr.entity.UserSearchEntity;
import at.dkepr.solrservice.PostRepository;
import at.dkepr.solrservice.UserRepository;


@Component
@EnableJms
public class JmsConsumer {

	@Autowired
	private UserRepository urepository;

	@Autowired
	private PostRepository prepository;
    
    @JmsListener(destination = "user-add-queue", containerFactory = "jmsListenerContainerFactory")
	public void receiveadd(UserSearchEntity message){
		System.out.println(message.getId() + " " + message.getFirstname()+ " " + message.getLastname() + " " + message.getEmail());
		//save the new user to the solr database
		this.urepository.save(message);
	}

	@JmsListener(destination = "user-delete-queue", containerFactory = "jmsListenerContainerFactory")
	public void receivedelete(UserSearchEntity message) {
		//delete the user
		this.urepository.deleteById(message.getId());
	}

	@JmsListener(destination = "posting-add-solr", containerFactory = "jmsListenerContainerFactory")
	public void receiveposting (Post message) {
		this.prepository.save(message);
	}

	@JmsListener(destination = "posting-delete-solr", containerFactory = "jmsListenerContainerFactory")
	public void deleteposting (ActiveMQTextMessage message) {
		String text = "";
		try {
			text = message.getText();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		if(text.contains("postid:")) {
            this.prepository.deleteById(text.substring(8, text.length()-1));
        }
        else{
            this.prepository.deleteAllByAuthorid(text.substring(10, text.length()-1));
        }       
		
		
	}

		
	
	


}
