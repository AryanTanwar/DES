package com.des.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.des.bean.FileUpload;
import com.des.dao.DAOFactory;
import com.des.email.EmailSender;

@WebServlet("/KeyAction")
public class KeyAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = null,getkey = null;
		DAOFactory dao = DAOFactory.getDAO();
		getkey = dao.getkey(request.getParameter("filename"));
		DAOFactory dao1 = DAOFactory.getDAO();
		
		byte[] file;
		String email = dao1.getemail(request.getParameter("name"));
		
		RequestDispatcher rd;
		
		EmailSender es = new EmailSender();
		es.userIdPassword("projectdes123@gmail.com", "qwertyritika");
		es.send(email, "projectdes123@gmail.com", "Mail Verification", "<html><body>Your Request File Key Is <br/>"+getkey+"</body></html>");
		
		DAOFactory dao3 = DAOFactory.getDAO();
		dao3.changeCount(request.getParameter("filename"));
		
		response.sendRedirect("admin/coustomrequest.jsp");
		
	}

}
