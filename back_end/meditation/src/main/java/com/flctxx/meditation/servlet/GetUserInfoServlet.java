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
import com.flctxx.meditation.bean.UserInfo;
import com.flctxx.meditation.service.UserService;
import com.flctxx.meditation.utils.NetworkUtility;



/**
 * Servlet implementation class getUserInfoServlet
 */

public class GetUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfoServlet() {
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
		if (userId==null){
	      	 out.print("invalid prameters");
            logger.info("invalid prameters");
            response.setStatus(400);
            return;
		}
		
		List<UserInfo> resList = userService.getUserInfoById(userId);
		if (resList!=null && resList.size()==1) {
			String resJsonStr= JSONObject.toJSONString(resList.get(0));
			out.println(resJsonStr);
		}else {
			out.print("get failed");
            logger.info("get failed");
		}
	}
	
	private static final Logger logger = Logger.getLogger(GetUserInfoServlet.class);
}
