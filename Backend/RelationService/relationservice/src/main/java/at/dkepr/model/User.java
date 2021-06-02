package at.dkepr.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class User {

	@Id
	private Long id;

	@Relationship(type="FOLLOWS")
	private List<Long> follows;

	public User(Long id, List<Long> follows) {
		this.id = id;
		this.follows = follows;
	}	

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getFollows() {
		return follows;
	}

	public void setFollows(List<Long> follows) {
		this.follows = follows;
	}
}
