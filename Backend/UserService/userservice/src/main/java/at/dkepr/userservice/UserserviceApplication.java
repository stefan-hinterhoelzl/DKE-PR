package at.dkepr.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class UserserviceApplication implements CommandLineRunner {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	public void run(String...args) throws Exception {
		System.out.println("Reading Users...");
        jdbcTemplate.query("SELECT * FROM users", (rs)-> {
            System.out.println(rs.getString("email")+" "+rs.getString("firstname")+" "+rs.getString("lastname")+" "+rs.getString("phonenumber")+" "+rs.getString("password"));          
        });
		
	}

}
