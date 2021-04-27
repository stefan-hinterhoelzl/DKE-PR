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
    
    @JmsListener(destination = "user-search-queue", containerFactory = "jmsListenerContainerFactory")
	public void receive(UserSearchEntity message){

		//save the new user to the solr database
		this.repository.save(message);


	}
}
