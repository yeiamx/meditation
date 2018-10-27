package com.flctxx.meditation.trial;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.AbstractTest;
import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.utils.NetworkUtility;

public class NotificationTrial {
	
	private static final Logger logger = Logger.getLogger(Notification.class);
    public static final String resUrlStarter = "http://39.108.226.195:8080/resources";
    public static final String urlStarter = "http://39.108.226.195:8080/meditation";
    
	public static void main(String[] args) {
		insertLesson();
		insertMusic();
		getMusic();
	}
	
	public static void getMusic(){
		JSONObject idObject = new JSONObject();
		idObject.put("type", "music");
		String jsonString = JSON.toJSONString(idObject);
		String postUrl = urlStarter + "/getNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		List<Notification> resArray = JSON.parseArray(resJsonString, Notification.class);
		for (Notification notification:resArray){
			logger.info(notification.getType());
		}
		//assertTrue(resArray.get(0).getTitle().equals("Notification"));	
	}
	
	public static void insertMusic(){
		String[] typeNames = {"rainwave", "soundeffect", "echoesofnature"};
		for(String type:typeNames){
			for (int i=1; i<=5; i++){
				Random ran = new Random(System.currentTimeMillis());
				
				Notification music = new Notification();
				music.setContent("");
				music.setId(ran.nextInt()+"");
				music.setImgUrl(resUrlStarter+"/img/"+type+"_"+i+".jpg");
				music.setResUrl(resUrlStarter+"/music/"+type+"_"+i+".mp3");
				music.setTime("");
				music.setTitle(type+" "+i);
				music.setType("music_"+type);
				
				String jsonString = JSON.toJSONString(music);
				String postUrl = urlStarter + "/updateNotification";
				String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
			}
		}
	}
	
	public static void insertLesson(){
		String type="lesson";
		for (int i=1; i<=7; i++){
			Random ran = new Random(System.currentTimeMillis());
			
			Notification music = new Notification();
			music.setContent("");
			music.setId(ran.nextInt()+"");
			music.setImgUrl(resUrlStarter+"/img/"+type+"_"+i+".jpg");
			music.setResUrl(resUrlStarter+"/music/"+type+i+".mp3");
			music.setTime("");
			music.setTitle("Day"+i);//Left
			music.setType("music_"+type);
			
			String jsonString = JSON.toJSONString(music);
			String postUrl = urlStarter + "/updateNotification";
			String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		}
	}
	
	
	public static void insertArticle(){
		Notification notification = new Notification();
		notification.setId("88");
		notification.setTitle("wo");
		notification.setContent("jjjjjjjjjjjjjj");
		notification.setTime("20181024");
		notification.setType("journal_test");
		String jsonString = JSON.toJSONString(notification);
		String postUrl = urlStarter + "/updateNotification";
		
		String resJsonString = NetworkUtility.postJson(postUrl, jsonString);
		JSONObject res = JSONObject.parseObject(resJsonString); 
		logger.info(resJsonString);
	}
	
  

}
