package com.flctxx.meditation.service;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.flctxx.meditation.bean.UserInfo;
import com.flctxx.meditation.dao.BaseDAO;
import com.flctxx.meditation.servlet.UpdateUserInfoServlet;

public class UserService {
	private BaseDAO dao = new BaseDAO();
	private final String TABLE_NAME = "com.flctxx.meditation.bean.UserInfo";
	private final String ID_COLUMN_NAME = "user_id";
	private final String[] iconUrls = new String[6];
	public static final String resUrlStarter = "http://39.108.226.195:8080/resources";
	private Random random;
	public UserService(){
		iconUrls[0] = resUrlStarter+"/img/snow.jpg";
		iconUrls[1] = resUrlStarter+"/img/RubyBeach.jpg";
		iconUrls[2] = resUrlStarter+"/img/nature1.jpg";
		iconUrls[3] = resUrlStarter+"/img/WhalePod.jpg";
		iconUrls[4] = resUrlStarter+"/img/xianqi.jpg";
		iconUrls[5] = resUrlStarter+"/img/sunset.jpg";
		random = new Random();
	}
	
	public Boolean updateUserInfo(UserInfo userInfo){
		return dao.saveOrUpdate(userInfo);
	}
	
	public List<UserInfo> getUserInfoById(String userId){
		return dao.listFromDifferId(userId, ID_COLUMN_NAME, TABLE_NAME);
	}
	
	public UserInfo login(String username, String password){
		List<UserInfo> list = dao.list("from "+TABLE_NAME);
		logger.info("length:"+list.size());
		for (int i=0; i<list.size(); i++){
			if (username.equals(list.get(i).getUserName()) && password.equals(list.get(i).getPassword())){
				return list.get(i);
			}
		}

		return null;
	}
	
	public Boolean register(String password, String userId, String userName){
		List<UserInfo> list = dao.list("from "+TABLE_NAME);
		for (int i=0; i<list.size(); i++){
			if (userName.equals(list.get(i).getUserName()))
				return false;
		}
		
		List<UserInfo> existUsers = dao.listFromDifferId(userId, ID_COLUMN_NAME, TABLE_NAME);
		if (existUsers==null || existUsers.size()>0){
			return false;
		}else {
			String imgUrl = iconUrls[random.nextInt(iconUrls.length)];
			UserInfo userInfo = new UserInfo(password, userId, userName, imgUrl, "");
			Boolean status = dao.saveOrUpdate(userInfo);
			if (!status){
				return false;
			}else {
				return true;
			}
		}
	}
	
	
	private static final Logger logger = Logger.getLogger(UserService.class);
}
