package at.dkepr.notificationservice;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert.*;
import junitparams.JUnitParamsRunner;

import at.dkepr.entity.Notification;
import at.dkepr.entity.NotificationRepository;
import at.dkepr.entity.NotificationWrapper;




@DataRedisTest
public class RedisCustomerRepositoryTest {

    @Autowired
    NotificationRepository repository;

    @Test
    public void testAdd() {

        NotificationWrapper wrapper = new NotificationWrapper(1L, "234", "Tester");
        wrapper.addNotification(new Notification(1L, "1234567890",10L ,true));
        wrapper.addNotification(new Notification(2L, "1234567891", 20L,false));
        wrapper.addNotification(new Notification(3L, "1234567892", 30L,false));
        wrapper = repository.save(wrapper);
        //Assert.assertNotNull(wrapper);
    }

    //Assert will nicht
    @Test
    public void testFindByAccounts() {
        List<NotificationWrapper> wrappers = repository.findByNotificationsId(3L);
       // Assert.assertEquals(1, wrappers.size());
        NotificationWrapper wrapper = wrappers.get(0);
       // Assert.assertNotNull(wrapper);
       // Assert.assertEquals(1, wrapper.getId().longValue());
    }


/*
    @Test
    public void testFindByExternal() {
        Customer customer = repository.findByExternalId("80010121098");
        Assert.assertNotNull(customer);
        Assert.assertEquals(1, customer.getId().longValue());
    }
    */


}