package com.flctxx.meditation.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.dao.BaseDAO;

public class NotificationService {
	private String tableName = "com.flctxx.meditation.bean.Notification";
	BaseDAO dao = new BaseDAO();
	
	public NotificationService(){
	}
	
	public List<Notification> getNotificationById(String id) {
		return dao.listFromId(id, tableName);
	}
	
	public List<Notification> getArticleNotification(){
		List<Notification> totalRes = dao.listAll(tableName);
		List<Notification> res = new ArrayList();
		for (Notification notification : totalRes){
			if (notification.getType().contains("article")){
				res.add(notification);
			}
		}
		
		return res;
	}
	public List<Notification> getMusicNotification(){
		List<Notification> totalRes = dao.listAll(tableName);
		List<Notification> res = new ArrayList();
		for (Notification notification : totalRes){
			if (notification.getType().contains("music")){
				res.add(notification);
			}
		}
		
		return res;
	}
	
	private static final Logger logger = Logger.getLogger(NotificationService.class);
}
