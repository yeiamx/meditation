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
import com.flctxx.meditation.service.UserService;
import com.flctxx.meditation.utils.NetworkUtility;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService service = new UserService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
			return;
		}
		
		String userName = parameters.getString("userName");
		String password = parameters.getString("password");
		if (userName==null || password==null){
	      	out.print("invalid prameters");
            logger.info("invalid prameters");
            return;
		}
		
		if (service.login(userName, password)){
			out.print("{status:true}");
		}else {
			out.print("{status:false}");
		}
	}
	
	private static final Logger logger = Logger.getLogger(LoginServlet.class);
}
