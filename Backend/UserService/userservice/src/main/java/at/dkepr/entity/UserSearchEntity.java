package at.dkepr.entity;

import java.io.Serializable;

public class UserSearchEntity implements Serializable {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String pokemonid;

    public UserSearchEntity() {}

    public UserSearchEntity(String id, String email, String firstname, String lastname, String pokemonid) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pokemonid = pokemonid;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getPokemonid() {
        return pokemonid;
    }

    public void setPokemonid(String pokemonid) {
        this.pokemonid = pokemonid;
    }

    public void setId(String id) {
        this.id = id;
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

    
}
