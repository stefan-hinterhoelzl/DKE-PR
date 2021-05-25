package at.dkepr.model;

import java.util.List;

import org.neo4j.driver.internal.shaded.reactor.util.annotation.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Getter;
import lombok.Setter;

@Node
public class User {

	@Id @GeneratedValue
	private Long id;

	@Getter
	@Setter
	private long relDBid; 

	@Relationship(type="FOLLOWS")
	private List<String> follows;


	@Relationship(type="FOLLOWED_BY")
	private List<String> followedByUsers;


	public User(Long id, long relDBid, List<String> follows, List<String> followedByUsers) {
		this.id = id;
		this.relDBid = relDBid;
		this.follows = follows;
		this.followedByUsers = followedByUsers;
	}

	

	public User() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public List<String> getFollows() {
		return follows;
	}


	public void setFollows(List<String> follows) {
		this.follows = follows;
	}


	public List<String> getFollowedByUsers() {
		return followedByUsers;
	}


	public void setFollowedByUsers(List<String> followedByUsers) {
		this.followedByUsers = followedByUsers;
	}



	
}
