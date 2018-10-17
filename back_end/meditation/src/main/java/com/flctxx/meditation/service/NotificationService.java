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
	public List articleIds;
	public List musicIds;
	
	
	public NotificationService(){
		articleIds = new ArrayList();
		musicIds = new ArrayList();
		
		//This should be use static resource.
		initIds();
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
	
	private void initIds(){
        Properties properties = new Properties();
        String musicStr;
        String articleStr;
        try {
            String filepath = AbstractTest.class.
                    getClassLoader().getResource("test.conf").getPath();
            if (filepath.contains("%"))
                filepath = URLDecoder.decode(filepath, "UTF-8");
            properties.loadFromXML(new FileInputStream(new File(filepath)));
            musicStr = (String) properties.get("music");
            articleStr = (String) properties.get("article");
            logger.info("musics: " + musicStr);
            logger.info("articles: " + articleStr);
        } catch (IOException e) {
            e.printStackTrace();
            musicStr = "";
            articleStr = "5,6,7";
            logger.info("configuration file is not found, " +
                    "using default article:5,6,7");
        }
        

        if (musicStr.length()>0){
        	String[] musicStrArray = musicStr.split(",");
            for (int i=0; i<musicStrArray.length; i++){
            	//logger.info(musicStrArray[i]);
            	musicIds.add(musicStrArray[i]);
            }
        }
        if (articleStr.length()>0){
        	String[] articleStrArray = articleStr.split(",");
            for (int i=0; i<articleStrArray.length; i++){
            	//logger.info(articleStrArray[i]);
            	articleIds.add(articleStrArray[i]);
            }
        }
 
 
	}
	
	private static final Logger logger = Logger.getLogger(NotificationService.class);
}
