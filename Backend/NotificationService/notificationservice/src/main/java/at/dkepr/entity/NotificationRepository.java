package at.dkepr.entity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<NotificationWrapper, List>{

    NotificationWrapper findByUserId(String UserId);
    List findByNotificationsId(Long id);

}