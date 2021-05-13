package at.dkepr.posttopicservice;

import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.dkepr.entity.Post;
import at.dkepr.entity.StringResponse;

@RestController
public class PostController {
    
    @Autowired
    private JmsTemplate jmsTemplate;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/post")
    public ResponseEntity<?> addPosting(@RequestBody Post post) {
        Topic topic = new ActiveMQTopic("posting-add-topic");

        jmsTemplate.convertAndSend(topic, post);
        return ResponseEntity.status(HttpStatus.OK).body(new StringResponse("Posting enqueued"));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/deletepost")
    public ResponseEntity<?> deletebyAuthorAndCreatedAt(@RequestBody Post post) {
        Topic topic = new ActiveMQTopic("posting-delete-topic");
        
        jmsTemplate.convertAndSend(topic, post);
        return ResponseEntity.status(HttpStatus.OK).body(new StringResponse("Deletion enqueued"));
    }
    
}
