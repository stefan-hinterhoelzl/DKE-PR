package at.dkepr.entity;
import org.springframework.data.redis.core.index.Indexed;
//import org.springframework.data.redis.core.RedisHash;

//@RedisHash
public class Notification {

    @Indexed private Long id;
    private String text;
    private long createdAt;
    private boolean read;


    
    public Notification(Long id, String text, long createdAt, boolean read) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.read = read;

        //test again
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getText() {
        return text;
    }



    public void setText(String text) {
        this.text = text;
    }



    public long getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }



    public boolean isRead() {
        return read;
    }



    public void setRead(boolean read) {
        this.read = read;
    }

    


}
