package at.dkepr.indexingservice;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class IndexingserviceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(IndexingserviceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8085"));
		app.run(args);
	}
}