package at.dkepr.queueservice;



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

	@JmsListener(destination = "posting-add-topic", containerFactory = "jmsListenerContainerFactoryTopic")
	public void receiveposting (Post message) {
		this.prepository.save(message);
	}

	@JmsListener(destination = "posting-delete-topic", containerFactory = "jmsListenerContainerFactoryTopic")
	public void deleteposting (Post message) {
		this.prepository.deleteById(message.getId());
	}


}
