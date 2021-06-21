package at.dkepr.notificationservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.dkepr.entity.Notification;
import at.dkepr.entity.NotificationRepository;
import at.dkepr.entity.NotificationWrapper;
import at.dkepr.entity.StringResponse;

@RestController
public class NotificationController {


    @Autowired
    private NotificationRepository repository;

    @PostMapping("notifications/user/{id}")
    private ResponseEntity<?> postUserWrapperObject(@PathVariable Long id) {
        NotificationWrapper newWrapper = new NotificationWrapper(id);

        return ResponseEntity.status(HttpStatus.OK).body(this.repository.save(newWrapper));   
    }

    @PutMapping("notifications/user/{id}")
    private ResponseEntity<?> putUserNotification(@PathVariable Long id, @RequestBody Notification not) {
        Optional<NotificationWrapper> wrapper = this.repository.findById(id);

        if(wrapper.isPresent()) {
            NotificationWrapper w = wrapper.get();
            List<Notification> list = w.getNotifications();
            list.add(not);
            w.setNotifications(list);
            return ResponseEntity.status(HttpStatus.OK).body(this.repository.save(w));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StringResponse("Wrapper Objekt für diesen User wurde nicht gefunden."));   
    }

    @GetMapping("notifications/user/{id}")
    private ResponseEntity<?> getUserNotifications(@PathVariable Long id) {
        Optional<NotificationWrapper> wrapper = this.repository.findById(id);
        
        if(wrapper.isPresent()) {
            NotificationWrapper w = wrapper.get();
            List<Notification> list = w.getNotifications();
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StringResponse("Wrapper Objekt für diesen User wurde nicht gefunden."));   
    }

    @DeleteMapping("notifications/user/{id}")
    private ResponseEntity<?> deleteUserNotificationWrapper(@PathVariable Long id) {
        this.repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new StringResponse("Wrapper Objekt gelöscht."));   
    }

    @PutMapping("notifications/user/{uid}/notification/{nid}")
    private ResponseEntity<?> setNotificationToRead(@PathVariable Long uid, @PathVariable String nid) {
    Optional<NotificationWrapper> wrapper = this.repository.findById(uid);
        
        if(wrapper.isPresent()) {
            NotificationWrapper w = wrapper.get();
            List<Notification> list = w.getNotifications();

            Notification not = null;

            for (int i = 0; i<list.size(); i++) {
                if (list.get(i).getId().equals(nid)) {
                    not = list.get(i);
                    list.remove(i);
                }
            }

            if (not != null) {
                not.setRead(true);
                list.add(not);
                w.setNotifications(list);
                return ResponseEntity.status(HttpStatus.OK).body(this.repository.save(w));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StringResponse("Notification mit dieser ID wurde nicht gefunden."));  
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StringResponse("Wrapper Objekt für diesen User wurde nicht gefunden."));   
    }

    @DeleteMapping("notifications/user/{id}/notifications")
    private ResponseEntity<?> deleteUserNotifications(@PathVariable Long id) {
        Optional<NotificationWrapper> wrapper = this.repository.findById(id);
        
        if(wrapper.isPresent()) {
            NotificationWrapper w = wrapper.get();
            w.setNotifications(new ArrayList<Notification>());
            return ResponseEntity.status(HttpStatus.OK).body( this.repository.save(w));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StringResponse("Wrapper Objekt für diesen User wurde nicht gefunden."));   
    }
    


} 
