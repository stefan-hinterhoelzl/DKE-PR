package at.dkepr.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
public class Post {
    
    @Id
    private final Long id;
    @Field
    private String emoticon;
    @Field
    private String content;    
    @Field
    private Date date;
    @Field
    private Long user;   

    public Post(Long id, String emoticon, String content, Date date, Long user) {
        this.id = id;
        this.emoticon = emoticon;
        this.content = content;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getEmoticon() {
        return emoticon;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Long getUser() {
        return user;
    }

}