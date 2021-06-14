package at.dkepr;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationserviceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication( NotificationserviceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8088"));
		app.run(args);

	}
}


