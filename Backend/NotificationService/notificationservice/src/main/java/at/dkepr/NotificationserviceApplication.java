package at.dkepr;

import java.util.Collections;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import at.dkepr.entity.Notification;
import at.dkepr.entity.NotificationRepository;

@SpringBootApplication
public class NotificationserviceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication( NotificationserviceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8088"));
		app.run(args);

	}
}


