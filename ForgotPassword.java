package com.des.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.des.bean.User;
import com.des.dao.DAOFactory;
import com.des.email.EmailSender;

@WebServlet("/ForgotPassword")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		String email = request.getParameter("email");
		User ur = new User();
		EmailSender es = new EmailSender();
		
		DAOFactory dao = DAOFactory.getDAO();
		ur.setEmail(email);
		
		if(dao.emailObject(ur)!=null) {
			es.userIdPassword("projectdes123@gmail.com", "qwertyritika");
			es.send(email, "projectdes123@gmail.com", "Mail Verification", "<html><body>To Change Password <br/><a href='http://localhost:9999/DES/sendpassword.jsp?email="+email+"'>Click Here</a></body></html>");
			rd = request.getRequestDispatcher("forgotpassword.jsp");
		}
		else {
			request.setAttribute("error","Email is not registered please register your email");
			rd = request.getRequestDispatcher("forgotpassword.jsp");
		}
		rd.forward(request, response);
	}

}
