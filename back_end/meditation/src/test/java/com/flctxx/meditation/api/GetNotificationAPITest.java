package com.flctxx.meditation.api;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.utils.NetworkUtility;


public class GetNotificationAPITest extends AbstractTest{
	/*@Test
	public void Test0() {
		JSONObject idObject = new JSONObject();
		idObject.put("id", "0");
		String jsonString = JSON.toJSONString(idObject);
		postUrl = urlStarter + "/homeNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		//logger.info(resJsonString);
		List<Notification> resArray = JSON.parseArray(resJsonString, Notification.class);
		assertTrue(resArray.size()>0);
		//assertTrue(resArray.get(0).getTitle().equals("Notification"));	
	}
	
	
	@Test
	public void Test1() {
		JSONObject idObject = new JSONObject();
		idObject.put("type", "article");
		String jsonString = JSON.toJSONString(idObject);
		postUrl = urlStarter + "/getNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		logger.info(resJsonString);
		List<Notification> resArray = JSON.parseArray(resJsonString, Notification.class);
		assertTrue(resArray.size()>0);
		//assertTrue(resArray.get(0).getTitle().equals("Notification"));	
	}
	
	@Test
	public void Test2() {
		JSONObject idObject = new JSONObject();
		idObject.put("type", "music");
		String jsonString = JSON.toJSONString(idObject);
		postUrl = urlStarter + "/getNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		logger.info(resJsonString);
		List<Notification> resArray = JSON.parseArray(resJsonString, Notification.class);
		assertTrue(resArray.size()>0);
		//assertTrue(resArray.get(0).getTitle().equals("Notification"));	
	}*/
	
	@Test
	public void Test3() {
		JSONObject idObject = new JSONObject();
		idObject.put("type", "favorite");
		idObject.put("favorites", "75124097,300135957");
		//idObject.put("favorites", "15403256,49645908");
		String jsonString = JSON.toJSONString(idObject);
		postUrl = urlStarter + "/getNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		logger.info(resJsonString);
		List<Notification> resArray = JSON.parseArray(resJsonString, Notification.class);
		assertTrue(resArray.size()==2);
	}
	
	private static final Logger logger = Logger.getLogger(GetNotificationAPITest.class);
}
