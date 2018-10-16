package com.flctxx.meditation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.flctxx.meditation.bean.Notification;
import com.flctxx.meditation.service.NotificationService;

/**
 * Servlet implementation class GetNotification
 */

public class GetNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private NotificationService notificationService = new NotificationService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNotificationServlet() {
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
		
		
		List<Notification> notificationRes = notificationService.getArticleNotification();
		String resJsonStr= JSONArray.toJSONString(notificationRes);
		out.print(resJsonStr);
	}

}
