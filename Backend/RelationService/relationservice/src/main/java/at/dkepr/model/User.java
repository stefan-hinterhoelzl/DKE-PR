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
	private List<String> follows;


	@Relationship(type="FOLLOWED_BY")
	private List<String> followedByUsers;


	public User(String id, List<String> follows, List<String> followedByUsers) {
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
