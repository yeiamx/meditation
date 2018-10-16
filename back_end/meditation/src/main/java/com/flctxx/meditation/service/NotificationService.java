package com.flctxx.meditation.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.dao.BaseDAO;

public class NotificationService {
	private String tableName = "com.flctxx.meditation.bean.Notification";
	BaseDAO dao = new BaseDAO();
	private List articleIds;
	private List musicIds;
	
	
	public NotificationService(){
		articleIds = new ArrayList();
		musicIds = new ArrayList();
		
		//This should be use static resource.
		articleIds.add("5");
		articleIds.add("6");
		articleIds.add("7");
	}
	
	public List<Notification> getNotificationById(String id) {
		return dao.listFromId(id, tableName);
	}
	
	public List<Notification> getArticleNotification(){
		List<Notification> totalRes = dao.listAll(tableName);
		List<Notification> res = new ArrayList();
		for (Notification notification : totalRes){
			if (articleIds.contains(notification.getId())){
				res.add(notification);
			}
		}
		
		return res;
	}
	public List<Notification> getMusicNotification(){
		List<Notification> totalRes = dao.listAll(tableName);
		List<Notification> res = new ArrayList();
		for (Notification notification : totalRes){
			if (musicIds.contains(notification.getId())){
				res.add(notification);
			}
		}
		
		return res;
	}
	
	private static final Logger logger = Logger.getLogger(NotificationService.class);
}
