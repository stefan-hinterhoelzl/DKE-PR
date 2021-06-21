package at.dkepr.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash
public class NotificationWrapper {
    
    @Id private Long id;
    @Indexed private String UserId;
    private String name;
    private List<Notification> notifications = new ArrayList<>();

    public NotificationWrapper(Long id, String UserId, String name) {
        this.id = id;
        this.UserId = UserId;
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserlId() {
        return UserId;
    }

    public void setUserlId(String UserId) {
        this.UserId = UserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Notification> getAccounts() {
        return notifications;
    }

    public void setAccounts(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addAccount(Notification noti) {
        this.notifications.add(noti);
    }

}
