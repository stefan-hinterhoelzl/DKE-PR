package at.dkepr.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
public class Post {
    @Id
    private String id;

    @Field
    private String content;

    @Field
    private String mood;

    @Field
    private String authorid;

    @Field 
    private String authorname;

    @Field
    private Long createdAt;


    //Required
    public Post() {
    }

    public Post(String id, String content, String mood, String authorid, String authorname, Long createdAt) {
        this.id = id;
        this.content = content;
        this.mood = mood;
        this.authorid = authorid;
        this.authorname = authorname;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
    
}
