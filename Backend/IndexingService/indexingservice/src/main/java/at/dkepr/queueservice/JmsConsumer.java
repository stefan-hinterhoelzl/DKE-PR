package at.dkepr.queueservice;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import at.dkepr.entity.UserSearchEntity;
import at.dkepr.solrservice.UserRepository;


@Component
@EnableJms
public class JmsConsumer {

	@Autowired
	private UserRepository repository;
    
    @JmsListener(destination = "user-add-queue", containerFactory = "jmsListenerContainerFactory")
	public void receiveadd(UserSearchEntity message){

		System.out.println(message.getId() + " " + message.getFirstname()+ " " + message.getLastname() + " " + message.getEmail());
		//save the new user to the solr database
		this.repository.save(message);
	}

	@JmsListener(destination = "user-delete-queue", containerFactory = "jmsListenerContainerFactory")
	public void receivedelete(UserSearchEntity message) {
		//delete the user
		this.repository.deleteById(message.getId());
	}


}
