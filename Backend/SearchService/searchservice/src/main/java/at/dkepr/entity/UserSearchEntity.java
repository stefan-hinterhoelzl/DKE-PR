package at.dkepr.entity;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "user")
public class UserSearchEntity implements Serializable {
    @Id
    @Field
    private String id;

    @Field
    private String email;

    @Field
    private String firstname;

    @Field
    private String lastname;

    @Field
    private String pokemonid;

    public UserSearchEntity() {}

    public UserSearchEntity(String id, String email, String firstname, String lastname, String pokemonid) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pokemonid = pokemonid;
    }

    public String getPokemonid() {
        return pokemonid;
    }

    public void setPokemonid(String pokemonid) {
        this.pokemonid = pokemonid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
