package at.dkepr.entity;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "DKEPR")
public class UserSearchEntity implements Serializable {

    @Id
    @Field
    private String id;

    @Field
    private Long uid;

    @Field
    private String email;

    @Field
    private String firstname;

    @Field
    private String lastname;

    public UserSearchEntity() {}

    public UserSearchEntity(String email, String firstname, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUID() {
        return uid;
    }

    public void setUID(Long uid) {
        this.uid = uid;
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
