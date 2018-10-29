package com.flctxx.meditation.api;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.utils.NetworkUtility;

public class LoginAPITest extends AbstractTest{
	@Test
	public void test0(){
		JSONObject idObject = new JSONObject();
		idObject.put("userName", "yeiamx");
		idObject.put("password", "asasas4444");
		String jsonString = JSON.toJSONString(idObject);
		postUrl = urlStarter + "/login";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		logger.info(resJsonString);
		//JSONObject resJsonObject = (JSONObject) JSONObject.parse(resJsonString);
		//assertTrue(resJsonObject.getString("status").equals("true"));
	}

	private static final Logger logger = Logger.getLogger(LoginAPITest.class);
}
