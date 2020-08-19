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
import com.des.email.EmailSender;

@WebServlet("/RegisterAction")
public class RegisterAction extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id;
		String f_name,l_name,u_name,email,password,cpass, phoneno;
		
		RequestDispatcher rd;
		f_name    =  request.getParameter("f_name");
		l_name    =  request.getParameter("l_name");
		u_name    =  request.getParameter("u_name");  
		email     =  request.getParameter("email");
		phoneno   =  request.getParameter("phoneno");
		password  =  request.getParameter("pass");
		cpass     =  request.getParameter("cpass");
		
		
		DAOFactory dao = DAOFactory.getDAO();
		
		if(!cpass.equals(password))
		{
			request.setAttribute("error", "Password & Conform Password Not Match");
			rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
		else if(dao.checkUserName(u_name))
		{
			request.setAttribute("error", "User Name Already Exit");
			rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
		else if(dao.checkEmail(email))
		{
			request.setAttribute("error", "Email Already Exit");
			rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
		else
		{   
			
			User user = new User();
			
			user.setF_name(f_name);
			user.setL_name(l_name);
			user.setU_name(u_name);
			user.setEmail(email);
			user.setPhoneno(phoneno);
			user.setPassword(password);

			User usr = dao.register(user);
			
			if(usr == null)
			{
				request.setAttribute("error", "Not Register please Check Details and Try Again");
				rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				
			}
			else
			{   
				EmailSender es = new EmailSender();
				es.userIdPassword("projectdes123@gmail.com", "qwertyritika");
				es.send(email, "projectdes123@gmail.com", "Mail Verification", "<html><body>please verify account <br/><a href='http://localhost:9999/DES/verify.jsp?email="+email+"'>Verify</a></body></html>");
				
				rd = request.getRequestDispatcher("activation.jsp");
				rd.forward(request, response);
			}
		}
	}
}
