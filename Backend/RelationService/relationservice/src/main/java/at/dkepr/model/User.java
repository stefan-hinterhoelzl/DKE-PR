package at.dkepr.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Node
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private long id;

	//No other Fields necessary



	@Getter
	@Setter
	@Relationship(type="FOLLOWS")
	private List<User> follows;

	@Getter
	@Setter
	@Relationship(type="FOLLOWED_BY")
	private List<User> followedByUsers;
}
