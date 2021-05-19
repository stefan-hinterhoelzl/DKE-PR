package at.dkepr.entity;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "post")
public class Post {

    @Id
    @Field
    private String id;

    @Field
    private String authorid;

    @Field
    private String authorname;

    @Field
    private String content;

    @Field
    private String createdAt;

    @Field
    private String mood;

    public Post(String id, String authorid, String authorname, String content, String createdAt, String mood) {
        this.id = id;
        this.authorid = authorid;
        this.authorname = authorname;
        this.content = content;
        this.createdAt = createdAt;
        this.mood = mood;
    }

    public Post() {
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

}
