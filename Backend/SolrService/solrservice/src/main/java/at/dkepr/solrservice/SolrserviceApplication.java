package at.dkepr.solrservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SolrserviceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SolrserviceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
		app.run(args);
	}
}
