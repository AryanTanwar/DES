package com.des.dao;

import java.util.ArrayList;

import com.des.bean.Contact;
import com.des.bean.FileUpload;
import com.des.bean.User;


public abstract class DAOFactory {

	public static DAOFactory getDAO() 
	{
		DAOFactory dao = null;
		dao = new SqlDAO();
		return dao;
		
	}
	
	public abstract User register(User user);
	public abstract User emailObject(User ur);
	public abstract User checkLogin(String uname, String password);
	public abstract String name(int id);
	public abstract String uname(int user_id);
	public abstract int countuser();
	public abstract int countfile();
	public abstract int countrequest();
	public abstract int countcontact();
	public abstract int userId(String filename);
	public abstract int userIdupload(int id);
	
	public abstract boolean changeStatus(String email);
	public abstract boolean changePassword(String email, String pass);
	public abstract boolean changeCount(String filename);
	public abstract boolean changeCountKey(String filename);
	
	public abstract boolean checkUserName(String name);
	public abstract boolean checkEmail(String email);	
	public abstract boolean fileUpload(FileUpload upload);
	public abstract boolean checkName(String name);
	public abstract boolean contact(Contact cont);
	public abstract boolean request(FileUpload req);
	public abstract boolean delete(int id);
	public abstract boolean deleter(int id);
	public abstract String getkey(String filename);
	public abstract String getemail(String name);
	public abstract int getid(String filename);
	
	public abstract ArrayList<FileUpload> alldata1(String filename);
	public abstract ArrayList<FileUpload> alldata();
	public abstract ArrayList<FileUpload> data();
	public abstract ArrayList<Contact> cdata();
	public abstract ArrayList<FileUpload> requestdata();
	public abstract ArrayList<User> data1();
	public abstract ArrayList<FileUpload> search(String srch);
	public abstract ArrayList<FileUpload> requestuserdata(int id);
	public abstract ArrayList<FileUpload> customRequest(int user_id); 
	public abstract ArrayList<FileUpload> Request(int id);
	public abstract User userdata(String firstname);
	
	
		
	}