package com.flctxx.meditation.api;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.utils.NetworkUtility;

public class GetUserInfoAPITest extends AbstractTest{
	private String idsample = "925166340@qq.com";
	private String pwdsample = "xxxx";
	private String namesample = "yeiamx";
	
	@Before
	public void prepare() {
		JSONObject userObject = new JSONObject();
		userObject.put("userId", idsample);
		userObject.put("password", pwdsample);
		userObject.put("userName", namesample);
		String jsonString = JSON.toJSONString(userObject);
		postUrl = urlStarter + "/updateUserInfo";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		logger.info(resJsonString);
		JSONObject resJsonObject = (JSONObject) JSONObject.parse(resJsonString);
		assertTrue(resJsonObject.getString("status").equals("true"));
	}
	
	@Test
	public void Test0() {
		JSONObject idObject = new JSONObject();
		idObject.put("userId", "925166340@qq.com");
		String jsonString = JSON.toJSONString(idObject);
		postUrl = urlStarter + "/getUserInfo";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		logger.info(resJsonString);
		JSONObject resJsonObject = (JSONObject) JSONObject.parse(resJsonString);
		assertTrue(resJsonObject.getString("password").equals(pwdsample));
		assertTrue(resJsonObject.getString("userName").equals(namesample));
	}
	
	private static final Logger logger = Logger.getLogger(GetUserInfoAPITest.class);
}
