package com.flctxx.meditation.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.dao.BaseDAO;

public class NotificationService {
	private String tableName = "com.flctxx.meditation.bean.Notification";
	BaseDAO dao = new BaseDAO();
	
	public List<Notification> getNotificationById(String id) {
		return dao.listFromId(id, tableName);
	}
	
	private static final Logger logger = Logger.getLogger(NotificationService.class);
}
