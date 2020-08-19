package com.des.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.des.bean.FileUpload;
import com.des.dao.DAOFactory;

@WebServlet("/SearchAction")
public class SearchAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		FileUpload upload = new FileUpload();
		ArrayList<FileUpload> list;
		DAOFactory dao = DAOFactory.getDAO();
		
		String search;
		
		search = request.getParameter("search");
		
		upload.setSearch(search);
		
        list = dao.search(search);
		
		if(list!=null)
         {
				request.setAttribute("list", list);
				rd = request.getRequestDispatcher("document.jsp");
             	rd.forward(request, response);
         }   
         else
         {
         	rd = request.getRequestDispatcher("document.jsp");
 			rd.forward(request, response);
         }
	}

}
