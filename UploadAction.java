package com.des.action;
 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.des.bean.FileUpload;
import com.des.bean.User;
import com.des.dao.DAOFactory;
import com.des.encrypt.CryptoUtils;

@WebServlet("/UploadAction")
public class UploadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String UPLOAD_DIRECTORY = "Upload";
	private static final String UPLOAD_ENCRYPT_DIRECTORY = "/encrypt";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024*1024*50;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!ServletFileUpload.isMultipartContent(request)) 
		{
			PrintWriter writer = response.getWriter();
			writer.println("Request does not contain upload data");
			writer.flush();
			return;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		String uploadPath = getServletContext().getRealPath("")
				+ File.separator + UPLOAD_DIRECTORY+File.separator+UPLOAD_ENCRYPT_DIRECTORY;
		
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) 
		{
			uploadDir.mkdir();
		}

		try {
			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();
			
			HttpSession session = request.getSession(false);
			User usr=(User)session.getAttribute("user_detail");
			FileUpload upload1=new FileUpload();
			String fileupload,filetype,aboutfile;
			
			String key1 = "DigitalEncryptio";
			int i=0;
			while (iter.hasNext()) 
			{
				FileItem item = (FileItem) iter.next();
				if(i<2) {
					i++;
					if(item.getFieldName().equals("filetype"))
						upload1.setFiletype(item.getString());
					else
						upload1.setAboutfile(item.getString());
				}
				else {
					fileupload = item.getName();
					upload1.setFileupload(fileupload);
				}
				String Key;
				if (!item.isFormField()) 
				{   
					String fileName = new File(item.getName()).getName();
					String filePath = uploadPath + File.separator + fileName;
					File storeFile = new File(filePath);
					item.write(storeFile);
					
					File inputFile = new File(filePath);
					File encryptedFile = new File(filePath+".encrypted");
					File decryptedFile = new File(filePath+".decrypted");
					CryptoUtils.encrypt(key1, inputFile, encryptedFile);
					//CryptoUtils.decrypt(key1, encryptedFile, decryptedFile);
					
					inputFile.delete();
					upload1.setKey(key1.getBytes());
				}
			}
			
			RequestDispatcher rd;
			
			int user_id;
	    	
			user_id = usr.getId();
	    	fileupload = request.getParameter("fileupload");
	    	upload1.setUser_id(user_id);

	    	DAOFactory dao=DAOFactory.getDAO();
	    	
	        if(dao.checkName(fileupload))
	        {
	        	request.setAttribute("fileerror", "File Name Already Exit, Please Change the File Name & Upload It");
				rd = request.getRequestDispatcher("fileupload.jsp");
				//rd.forward(request, response);
	        	
	        }
	        else
	        {
	        	 boolean status=dao.fileUpload(upload1);
	        	 if(status==true)
	             {
	        		 	request.setAttribute("error", "Successfully File Uploaded");
		             	session.setAttribute("user_detail", upload);
		             	rd = request.getRequestDispatcher("fileupload.jsp");
		     	 }   
	             else
	             {
		             	request.setAttribute("error", "Unsuccessfull File Uploaded");
		             	rd = request.getRequestDispatcher("fileupload.jsp");
		     	 }
	        }
			request.setAttribute("message", "Upload has been done successfully!");
			//RequestDispatcher r = request.getRequestDispatcher("fileupload.jsp");
			rd.forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
	}
	}			
}