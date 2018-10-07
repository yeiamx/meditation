package com.flctxx.meditation.service;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.bean.UserInfo;

public class UserServiceTest extends AbstractTest{
	private UserService userService = new UserService();
	
	@Test
	public void test1(){
		UserInfo userSample = new UserInfo("xxxx", "925166340@qq.com", "yeiamx");
		assertTrue(userService.updateUserInfo(userSample));
	}
	
	@Test
	public void test2(){
		UserInfo userSample = new UserInfo("xxxx", "925166340@qq.com", "yeiamx");
		userService.updateUserInfo(userSample);
		List<UserInfo> userGetList = userService.getUserInfoById(userSample.getUserId());
		logger.info(userGetList.size());
		assertTrue(userGetList.get(0).getUserName().equals(userSample.getUserName()));
		assertTrue(userGetList.get(0).getPassword().equals(userSample.getPassword()));
	}
	
	@Test
	public void test3(){
		UserInfo userSample = new UserInfo("xxxx", "925166340@qq.com", "yeiamx");
		userService.updateUserInfo(userSample);
		assertTrue(userService.login("yeiamx", "xxxx"));
		assertTrue(!userService.login("yeiamx", "xxxxa"));
	}
	
	private static final Logger logger = Logger.getLogger(UserServiceTest.class);
}
