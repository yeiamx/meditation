package com.flctxx.meditation.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.flctxx.meditation.bean.UserInfo;
import com.flctxx.meditation.dao.BaseDAO;
import com.flctxx.meditation.servlet.UpdateUserInfoServlet;

public class UserService {
	private BaseDAO dao = new BaseDAO();
	private final String TABLE_NAME = "com.flctxx.meditation.bean.UserInfo";
	private final String ID_COLUMN_NAME = "user_id";
	
	public Boolean updateUserInfo(UserInfo userInfo){
		return dao.saveOrUpdate(userInfo);
	}
	
	public List<UserInfo> getUserInfoById(String userId){
		return dao.listFromDifferId(userId, ID_COLUMN_NAME, TABLE_NAME);
	}
	
	public Boolean login(String username, String password){
		List<UserInfo> list = dao.list("from "+TABLE_NAME);
		for (int i=0; i<list.size(); i++){
			if (username.equals(list.get(i).getUserName()) && password.equals(list.get(i).getPassword()))
				return true;
		}
		return false;
	}
	
	private static final Logger logger = Logger.getLogger(UserService.class);
}
