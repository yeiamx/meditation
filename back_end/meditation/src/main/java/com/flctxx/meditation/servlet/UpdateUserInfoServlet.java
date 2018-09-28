package com.flctxx.meditation.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flctxx.meditation.bean.UserInfo;
import com.flctxx.meditation.service.UserService;
import com.flctxx.meditation.utils.NetworkUtility;

/**
 * Servlet implementation class updateUserInfoServlet
 */

public class UpdateUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserInfoServlet() {
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
		JSONObject parameters = JSON.parseObject(jsonString);
		if (parameters==null) {
	      	 out.print("didnt pass correct json string");
           logger.info("didnt get correct json string");
           response.setStatus(400);
           return;
		}
		
		String userId = parameters.getString("userId");
		String userName = parameters.getString("userName");
		String password = parameters.getString("password");
		if (userId==null || userName==null || password==null){
	      	out.print("invalid prameters");
            logger.info("invalid prameters");
            response.setStatus(400);
            return;
		}
		
		UserInfo userInfo = new UserInfo(password, userId, userName);
		if (userService.updateUserInfo(userInfo)){
			out.println("{status:true}");
		}else {
			out.print("{status:false}");
            logger.info("update failed");
		}
		
	}
	
	private static final Logger logger = Logger.getLogger(UpdateUserInfoServlet.class);
}
