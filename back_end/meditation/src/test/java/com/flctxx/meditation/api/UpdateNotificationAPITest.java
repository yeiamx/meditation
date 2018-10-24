package com.flctxx.meditation.api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.utils.NetworkUtility;

public class UpdateNotificationAPITest extends AbstractTest{
	@Test
	public void test0(){
		Notification notification = new Notification();
		notification.setId("777");
		notification.setTitle("wo");
		notification.setContent("jjjjjjjjjjjjjj");
		notification.setTime("20181024");
		notification.setType("journal_test");
		String jsonString = JSON.toJSONString(notification);
		postUrl = urlStarter + "/updateNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		JSONObject res = JSONObject.parseObject(resJsonString); 
		assertTrue(res.getString("status").equals("true"));
		
		
		JSONObject object = new JSONObject();
		object.put("type", "journal");
		String parameters = object.toJSONString();
		postUrl = urlStarter + "/getNotification";
		
		resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		JSONArray array = JSONArray.parseArray(resJsonString);
		Boolean flag = false;
		for (int i=0; i<array.size(); i++){
			if (array.getJSONObject(i).getString("time").equals("20181024")){
				flag = true;
				break;
			}
		}
		assertTrue(flag);
		
	}
}
