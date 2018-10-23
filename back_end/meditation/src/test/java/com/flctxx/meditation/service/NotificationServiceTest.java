package com.flctxx.meditation.service;

import com.flctxx.meditation.AbstractTest;

import com.flctxx.meditation.bean.Notification;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class NotificationServiceTest extends AbstractTest{
	private static NotificationService notificationService = new NotificationService();

	@Test
	public void test1() {
		String id = "0";
		List<Notification> notifications = notificationService.getNotificationById(id);
		logger.info(notifications);
		assertTrue(notifications.size()>0);
		assertTrue(notifications.get(0).getTitle().equals("Notification"));
	}
	
	@Test
	public void test2() {
		List<Notification> notifications = notificationService.getNotification("article");
		logger.info(notifications.get(0).getContent());
		assertTrue(notifications.size()>0);
		
	}
	
	@Test
	public void test3() {
		List<Notification> notifications = notificationService.getNotification("music");
		logger.info(notifications.get(0).getContent());
		assertTrue(notifications.size()>0);
		
	}
	
	@Test
	public void test4() {
		Notification insert_notification = new Notification();
		insert_notification.setId("777");
		insert_notification.setType("journal");
		notificationService.updateNotification(insert_notification);
		
		List<Notification> notifications = notificationService.getNotification("journal");
		logger.info(notifications.get(0).getContent());
		assertTrue(notifications.size()>0);
	
	}
	

	
	private static final Logger logger = Logger.getLogger(NotificationServiceTest.class);
}
