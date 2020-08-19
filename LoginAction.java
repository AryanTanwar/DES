package com.des.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.des.bean.User;
import com.des.dao.DAOFactory;

@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user,pass;
		RequestDispatcher rd;
		
		user = request.getParameter("username");
		pass = request.getParameter("password");
		
		User usr=new User();
		DAOFactory dao = DAOFactory.getDAO();
		usr = dao.checkLogin(user, pass);
			
		if(usr != null)
		{
				if(usr.getRole().equals("admin"))
				  {
					 HttpSession session = request.getSession();
					 session.setAttribute("user_detail", usr);
					 rd = request.getRequestDispatcher("admin/home.jsp");
                  }
				  else
				  {
					  HttpSession session = request.getSession();
					  session.setAttribute("user_detail", usr);
					  rd = request.getRequestDispatcher("index.jsp");  
				   }
			}
			else
			{
				request.setAttribute("loginerror", "Username or Password not match");
				rd = request.getRequestDispatcher("login.jsp");
			}
		rd.forward(request, response);
	}
}