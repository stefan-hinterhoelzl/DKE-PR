package at.dkepr.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, String> {}