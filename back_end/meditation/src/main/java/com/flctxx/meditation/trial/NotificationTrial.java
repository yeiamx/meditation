package com.flctxx.meditation.trial;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.utils.NetworkUtility;

public class NotificationTrial extends AbstractTest{
	
	private static final Logger logger = Logger.getLogger(Notification.class);
	
	public static void main(String[] args) {
		getUrlStarter();
		
		// TODO Auto-generated method stub
		Notification notification = new Notification();
		notification.setId("88");
		notification.setTitle("wo");
		notification.setContent("jjjjjjjjjjjjjj");
		notification.setTime("20181024");
		notification.setType("journal_test");
		String jsonString = JSON.toJSONString(notification);
		postUrl = urlStarter + "/updateNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		JSONObject res = JSONObject.parseObject(resJsonString); 
		logger.info(resJsonString);
	}
	
    public static void getUrlStarter() {
        Properties properties = new Properties();
        try {
            String filepath = AbstractTest.class.
                    getClassLoader().getResource("test.conf").getPath();
            if (filepath.contains("%"))
                filepath = URLDecoder.decode(filepath, "UTF-8");
            properties.loadFromXML(new FileInputStream(new File(filepath)));
            urlStarter = (String) properties.get("url-starter");
            logger.info("Current API Test is using url-starter: " + urlStarter);
        } catch (IOException e) {
            e.printStackTrace();
            urlStarter = "http://localhost:8080";
            logger.info("Test configuration file is not found, " +
                    "using default [http://localhost:8080]");
        }
    }

}
