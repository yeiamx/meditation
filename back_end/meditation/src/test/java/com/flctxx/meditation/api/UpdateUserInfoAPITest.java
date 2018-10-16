package com.flctxx.meditation.api;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.utils.NetworkUtility;

public class UpdateUserInfoAPITest extends AbstractTest{
	
	
	@Test
	public void Test0() {
		JSONObject userObject = new JSONObject();
		userObject.put("userId", "925166340@qq.com");
		userObject.put("password", "xxxx");
		userObject.put("userName", "yeiamx");
		String jsonString = JSON.toJSONString(userObject);
		postUrl = urlStarter + "/updateUserInfo";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		JSONObject resJsonObject = (JSONObject) JSONObject.parse(resJsonString);
		assertTrue(resJsonObject.getString("status").equals("true"));
	}
	
	@Test
	public void Test1() {
		JSONObject userObject = new JSONObject();
		userObject.put("userId", "925166340@qq.com");
		userObject.put("password", "xxxxa");
		userObject.put("userName", "yeiamx");
		String jsonString = JSON.toJSONString(userObject);
		postUrl = urlStarter + "/updateUserInfo";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		JSONObject resJsonObject = (JSONObject) JSONObject.parse(resJsonString);
		logger.info(resJsonString);
		assertTrue(resJsonObject.getString("status").equals("false"));
	}
	
	private static final Logger logger = Logger.getLogger(UpdateUserInfoAPITest.class);
}
