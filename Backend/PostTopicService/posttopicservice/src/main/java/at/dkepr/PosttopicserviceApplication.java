package at.dkepr;


import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosttopicserviceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PosttopicserviceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8087"));
		app.run(args);
	}

}
