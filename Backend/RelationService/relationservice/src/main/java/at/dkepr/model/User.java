package at.dkepr.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("User")
public class User {

	@Id
	private Long id;;

	public User(Long id) {
		this.id = id;
	}	

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
