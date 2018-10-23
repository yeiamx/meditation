package com.flctxx.meditation.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.service.NotificationService;
import com.flctxx.meditation.utils.NetworkUtility;

/**
 * Servlet implementation class UpdateNotificationServlet
 */

public class UpdateNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NotificationService notificationService = new NotificationService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateNotificationServlet() {
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
		PrintWriter out = response.getWriter();
		String jsonString = NetworkUtility.exhaustBufferedReader(request.getReader());
		Notification notification = null;
		try {
			notification = JSONObject.parseObject(jsonString, Notification.class);
		} catch (Exception e){
	      	 out.print("didnt pass correct json string");
	         logger.info("didnt get correct json string");
	         return;
		}
		
		if (notificationService.updateNotification(notification)){
			out.println("{status:true}");
		} else {
			out.print("{status:false}");
            logger.info("update failed");
		}
		
	}
	private static final Logger logger = Logger.getLogger(UpdateNotificationServlet.class);
}
