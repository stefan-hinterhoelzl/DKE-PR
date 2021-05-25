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

	@Relationship(type="FOLLOWED_BY")
	private List<Long> followedByUsers;

	public User(Long id, List<Long> follows, List<Long> followedByUsers) {
		this.id = id;
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

	public List<Long> getFollows() {
		return follows;
	}

	public void setFollows(List<Long> follows) {
		this.follows = follows;
	}

	public List<Long> getFollowedByUsers() {
		return followedByUsers;
	}

	public void setFollowedByUsers(List<Long> followedByUsers) {
		this.followedByUsers = followedByUsers;
	}
}
