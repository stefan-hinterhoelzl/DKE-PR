package at.dkepr.entity;

import java.io.Serializable;

public class UserSearchEntity implements Serializable {
    private Long uid;
    private String email;
    private String firstname;
    private String lastname;

    public UserSearchEntity() {}

    public UserSearchEntity(Long uid, String email, String firstname, String lastname) {
        this.uid = uid;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public Long getUID() {
        return uid;
    }

    public void setUID(Long id) {
        this.uid = id;
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
