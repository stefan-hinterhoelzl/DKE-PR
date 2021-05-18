package at.dkepr.posttopicservice;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
        Queue queue1 = new ActiveMQQueue("posting-add-solr");
        Queue queue2 = new ActiveMQQueue("posting-add-couchbase");

        jmsTemplate.convertAndSend(queue1, post);
        jmsTemplate.convertAndSend(queue2, post);
        return ResponseEntity.status(HttpStatus.OK).body(new StringResponse("Posting enqueued"));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/deletepost")
    public ResponseEntity<?> deletebyPostID(@RequestBody Post post) {
        Queue queue1 = new ActiveMQQueue("posting-delete-solr");
        Queue queue2 = new ActiveMQQueue("posting-delete-couchbase");
        
        jmsTemplate.convertAndSend(queue1, post);
        jmsTemplate.convertAndSend(queue2, post);

        return ResponseEntity.status(HttpStatus.OK).body(new StringResponse("Deletion enqueued"));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/deleteAll/{authorid}")
    public ResponseEntity<?> deleteAllByAuthorID(@PathVariable String authorid) {
        Queue queue1 = new ActiveMQQueue("posting-delete-solr");
        Queue queue2 = new ActiveMQQueue("posting-delete-couchbase");
        
        jmsTemplate.convertAndSend(queue1, "authorid:"+authorid);
        jmsTemplate.convertAndSend(queue2, "authorid:"+authorid);

        return ResponseEntity.status(HttpStatus.OK).body(new StringResponse("Deletion enqueued"));
    }
    
}
