package com.flctxx.meditation.api;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.utils.NetworkUtility;

public class RegisterAPITest extends AbstractTest{
	private String idsample = "925366340@qq.com";
	private String pwdsample = "xxxx";
	private String namesample = "yeiamx";
	
	@Test
	public void test0(){
		JSONObject userObject = new JSONObject();
		userObject.put("userId", idsample);
		userObject.put("password", pwdsample);
		userObject.put("userName", namesample);
		String jsonString = JSON.toJSONString(userObject);
		postUrl = urlStarter + "/register";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		logger.info(resJsonString);
	}
	
	private static final Logger logger = Logger.getLogger(RegisterAPITest.class);
}
