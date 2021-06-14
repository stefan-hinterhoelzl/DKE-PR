package at.dkepr.entity;

import java.util.Date;
import java.io.Serializable;


import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class Notification implements Serializable {

    private String id;
    private String text;
    private Date createdAt;
    private boolean read;


    
    public Notification(String id, String text, Date createdAt, boolean read) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.read = read;

        //test again
    }



    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }



    public String getText() {
        return text;
    }



    public void setText(String text) {
        this.text = text;
    }



    public Date getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }



    public boolean isRead() {
        return read;
    }



    public void setRead(boolean read) {
        this.read = read;
    }

    


}
