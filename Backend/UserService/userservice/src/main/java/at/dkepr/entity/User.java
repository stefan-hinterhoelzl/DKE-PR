package at.dkepr.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Users")
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
	@SequenceGenerator(sequenceName = "user_seq", allocationSize = 1, name = "USER_SEQ")
	@Column(name = "id")
	long id;

	@Column(name = "email", unique = true, nullable = false)
	String email;

	@Column(name = "firstname", nullable = false)
	String firstname;

	@Column(name = "lastname", nullable = false)
	String lastname;

	@Column(name = "phonenumber")
	String phonenumber;

	@Column(name = "password", nullable = false)
	String password;

	@Column(name = "pokemonid", nullable = false)
	String pokemonid;




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPokemonid() {
		return pokemonid;
	}

	public void setPokemonid(String pokemonid) {
		this.pokemonid = pokemonid;
	}

	

}
