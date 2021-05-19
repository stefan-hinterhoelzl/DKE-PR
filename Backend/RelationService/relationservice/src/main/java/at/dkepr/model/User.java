package at.dkepr.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;





@Node
public class User {

	@Id
	private String id;

	@Relationship(type="FOLLOWS")
	private List<User> follows;


	@Relationship(type="FOLLOWED_BY")
	private List<User> followedByUsers;


	public User(String id, List<User> follows, List<User> followedByUsers) {
		this.id = id;
		this.follows = follows;
		this.followedByUsers = followedByUsers;
	}

	

	public User() {
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List<User> getFollows() {
		return follows;
	}


	public void setFollows(List<User> follows) {
		this.follows = follows;
	}


	public List<User> getFollowedByUsers() {
		return followedByUsers;
	}


	public void setFollowedByUsers(List<User> followedByUsers) {
		this.followedByUsers = followedByUsers;
	}



	
}
