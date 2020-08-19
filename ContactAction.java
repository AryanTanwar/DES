package com.des.action;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.des.bean.Contact;
import com.des.bean.User;
import com.des.dao.DAOFactory;

@WebServlet("/ContactAction")
public class ContactAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		User usr=(User)session.getAttribute("user_detail");
		
		RequestDispatcher rd;
		Contact cont = new Contact();
		int user_id;
		String name,mail,message;
	
		user_id = usr.getId();
		name = request.getParameter("name");
		mail = request.getParameter("mail");
		message = request.getParameter("message");
		
		DAOFactory dao = DAOFactory.getDAO();
		
		cont.setUser_id(user_id);
		cont.setName(name);
		cont.setMail(mail);
		cont.setMessage(message);
		
		boolean status = dao.contact(cont);
		
		if(status==true)
        {
            	request.setAttribute("error", "Your Message Has Sent Succesfully");
            	rd = request.getRequestDispatcher("contact.jsp");
    			rd.forward(request, response);
        }   
        else
        {
        	request.setAttribute("error", "Your Message Has Not Sent Succesfully");
        	rd = request.getRequestDispatcher("contact.jsp");
			rd.forward(request, response);
        }
	}
}
