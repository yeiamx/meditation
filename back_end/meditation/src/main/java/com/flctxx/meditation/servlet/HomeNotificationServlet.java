package com.flctxx.meditation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.service.NotificationService;
import com.flctxx.meditation.utils.NetworkUtility;

public class HomeNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NotificationService notificationService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeNotificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.setContentType("application/json;charset=UTF-8");
		 notificationService= new NotificationService();
		
		PrintWriter out = response.getWriter();
		String jsonString = NetworkUtility.exhaustBufferedReader(request.getReader());
		JSONObject parameters = JSON.parseObject(jsonString);
		
		if (parameters==null) {
	      	 out.print("didnt pass correct json string");
            logger.info("didnt get correct json string");
            response.setStatus(400);
            return;
		}
		
		String id = parameters.getString("id");
		if (id==null){
	      	 out.print("invalid prameters");
            logger.info("invalid prameters");
            response.setStatus(400);
            return;
		}
		
		List<Notification> notificationRes = notificationService.getNotificationById(id);
		String resJsonStr= JSONArray.toJSONString(notificationRes);
		out.print(resJsonStr);
	}
	
	private static final Logger logger = Logger.getLogger(HomeNotificationServlet.class);
}
