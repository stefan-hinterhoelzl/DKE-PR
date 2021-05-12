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
    private String author;

    @Field
    private String content;

    @Field
    private String createdAt;

    @Field
    private String mood;

    public Post(String id, String author, String content, String createdAt, String mood) {
        this.id = id;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
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
