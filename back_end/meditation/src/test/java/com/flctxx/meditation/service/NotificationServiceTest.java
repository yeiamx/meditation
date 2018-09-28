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
	
	private static final Logger logger = Logger.getLogger(NotificationServiceTest.class);
}
