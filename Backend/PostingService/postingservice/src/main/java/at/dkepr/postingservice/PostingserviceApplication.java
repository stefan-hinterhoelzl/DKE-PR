package at.dkepr.postingservice;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostingserviceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PostingserviceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8082"));
		app.run(args);
	}


}
