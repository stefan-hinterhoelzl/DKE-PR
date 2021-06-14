package at.dkepr.entity;

import java.util.Date;
import java.io.Serializable;


import org.springframework.data.redis.core.RedisHash;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Notification implements Serializable {

    private String id;
    private String text;
    private Date createdAt;
    private boolean read;


    /*
    public Notification(String id, String text, Date createdAt, boolean read) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.read = read;

        //test
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

    public Date getText(Date createdAt) {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
*/

}
