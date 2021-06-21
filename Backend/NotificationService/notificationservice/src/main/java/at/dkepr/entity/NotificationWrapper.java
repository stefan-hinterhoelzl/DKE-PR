package at.dkepr.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class NotificationWrapper {
    
    @Id private Long id;
    private List<Notification> notifications;

    public NotificationWrapper(Long id) {
        this.id = id;
        this.notifications = new ArrayList<Notification>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(Notification noti) {
        this.notifications.add(noti);
    }

}
