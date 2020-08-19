package com.des.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

import com.des.bean.Contact;
import com.des.bean.FileUpload;
import com.des.bean.User;

public class SqlDAO extends DAOFactory{

	Connection con;
	Statement st;
	ResultSet rs;
	int id;
	String query;
	int res = 0;
	User usr;
	FileUpload uploadfile;
	boolean flag = false;
	
	@Override
	public User register(User user) {
		flag = false;
		try {
			
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "insert into registration(firstname,lastname,username,email,phoneno,password,role,status) values "
					+ "('"+user.getF_name()+"','"+user.getL_name()+"','"+user.getU_name()+"','"+user.getEmail()+"','"+user.getPhoneno()+"','"+user.getPassword()+"','user','pending')";
			
			res = st.executeUpdate(query);
			
			if(res > 0)
			{
				usr = new User();
				
				usr.setEmail(user.getEmail());
				usr.setF_name(user.getF_name());
				usr.setL_name(user.getL_name());
				usr.setRole("user");
	
			}
			else
			{
				usr = null;
			}
			
			st.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usr;
	}

	@Override
	public boolean checkUserName(String name) {
		flag = false;
		try {
			
			con = DBConnection.getConnection();
			st = con.createStatement();
			query = "select username from registration where username = '"+name+"'";
			rs = st.executeQuery(query);
			
			if(rs.next())
				flag = true;
			else
				flag = false;
			
			con.close();
			st.close();
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean checkEmail(String email) {
		flag = false;
		try {
			
			con = DBConnection.getConnection();
			st = con.createStatement();
			query = "select email from registration where email = '"+email+"'";
			rs = st.executeQuery(query);
			
			if(rs.next())
				flag = true;
			else
				flag = false;
			
			con.close();
			st.close();
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public User checkLogin(String uname, String password) {
		User usr = new User();
		try {
			
			con = DBConnection.getConnection();
			st = con.createStatement();
			query = "select * from registration where email = '"+uname+"' and password = '"+password+"' and status = 'active' ";
			rs = st.executeQuery(query);
	
			if(rs.next())
			{
				
				
				usr.setId(rs.getInt("id"));
				usr.setEmail(rs.getString("email"));
				usr.setF_name(rs.getString("firstname"));
				usr.setL_name(rs.getString("lastname"));
				usr.setRole(rs.getString("role")); 
				
			}
			else
				
				usr = null;
			
			con.close();
			st.close();
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return usr;
	}
	
	 
	@Override
	public boolean fileUpload(FileUpload upload) {
		try {
			
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "insert into uploadfile(user_id,filename,filetype,aboutfile,date,filekey,status) values "
					+ "('"+upload.getUser_id()+"','"+upload.getFileupload()+"','"+upload.getFiletype()+"','"+upload.getAboutfile()+"',now(),'"+upload.getKey()+"','active')";
			res = st.executeUpdate(query);
 			
			st.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(res > 0)
			return true;	
		else
			return false;
		
	}

	@Override
	public boolean checkName(String name) {		
		flag = false;
		try {
			
			con = DBConnection.getConnection();
			st = con.createStatement();
			query = "select filename from uploadfile where filename = '"+name+"'";
			rs = st.executeQuery(query);
			
			if(rs.next())
				flag = true;
			else
				flag = false;
			
			con.close();
			st.close();
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
		
	}

	@Override
	public boolean contact(Contact cont) {
		try {
			
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "insert into contact(user_id,name,mail,message) values "
					+ "('"+cont.getUser_id()+"','"+cont.getName()+"','"+cont.getMail()+"','"+cont.getMessage()+"')";
			res = st.executeUpdate(query);

			st.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(res > 0)
			return true;	
		else
			return false;
	}

	@Override
	public ArrayList<FileUpload> alldata() {
		ArrayList<FileUpload> list = new ArrayList<FileUpload>();
		
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select user_id,filename,filetype from uploadfile where status = 'active'";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				FileUpload fu = new FileUpload();
				fu.setUser_id(rs.getInt("user_id"));
				fu.setFilename(rs.getString("filename"));
				fu.setFiletype(rs.getString("filetype"));
				list.add(fu);
			}
 
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList<FileUpload> data() {
		ArrayList<FileUpload> list = new ArrayList<FileUpload>();
		
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select * from uploadfile where status = 'active' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				User u = new User();
				FileUpload fu = new FileUpload();
				
				fu.setId(rs.getInt("id"));
				fu.setUser_id(rs.getInt("user_id"));
				fu.setFilename(rs.getString("filename"));
				fu.setFiletype(rs.getString("filetype"));
				fu.setAboutfile(rs.getString("aboutfile"));
				fu.setDate(rs.getString("date"));
				list.add(fu);
			}
 
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList<User> data1() {
		ArrayList<User> list = new ArrayList<User>();

		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select * from registration where role = 'user' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setF_name(rs.getString("firstname"));
				u.setL_name(rs.getString("lastname"));
				u.setU_name(rs.getString("username"));
				u.setPhoneno(rs.getString("phoneno"));
				u.setEmail(rs.getString("email"));
				u.setRole(rs.getString("role"));
				u.setStatus(rs.getString("status"));
				list.add(u);
			}
 
			st.close();
			con.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean delete(int id) {
		boolean flag = false;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "update uploadfile set status = 'Stop' where id = '"+id+"' ";
			st.executeUpdate(query);
			
			flag = true;
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public ArrayList<FileUpload> search(String srch) {
		ArrayList<FileUpload> list = new ArrayList<FileUpload>();
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select * from uploadfile where filename like '%"+srch+"%'";
			rs = st.executeQuery(query);
			System.out.println(query);

			while(rs.next()) 
			{
				FileUpload fu = new FileUpload();
				fu.setFilename(rs.getString("filename"));
				fu.setFiletype(rs.getString("filetype"));
				list.add(fu);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String name(int id) {
		String uname=null;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select firstname from registration where id = '"+id+"' ";
			rs = st.executeQuery(query);
			System.out.println(query);
			
			while(rs.next()) 
			{
				uname=rs.getString("firstname");
			}
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uname;
	}

	@Override
	public int countuser() {
		int i = 0;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select count(id) from registration ";
			rs = st.executeQuery(query);
			
			if(rs.next())
			{
				i = rs.getInt(1);
			}
			
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int countfile() {
		int i = 0;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select count(id) from uploadfile where status = 'active' ";
			rs = st.executeQuery(query);
			
			if(rs.next())
			{
				i = rs.getInt(1);
			}
			
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public boolean request(FileUpload req) {
		try {
			
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "insert into request (user_id,user_idupload,filename,filetype,status,count) values "
					+ "('"+req.getUser_id()+"','"+req.getUser_idupload()+"','"+req.getFilename()+"','"+req.getFiletype()+"','active','0')";
			res = st.executeUpdate(query);

			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(res > 0)
			return true;	
		else
			return false;
	}

	@Override
	public ArrayList<FileUpload> requestdata() {
		ArrayList<FileUpload> list = new ArrayList<FileUpload>();
		//ArrayList<User> list1 = new ArrayList<User>();
		
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select * from request ";
	
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				FileUpload fu = new FileUpload();
				
				fu.setId(rs.getInt("id"));
				fu.setUser_id(rs.getInt("user_id"));
				fu.setFilename(rs.getString("filename"));
				fu.setFiletype(rs.getString("filetype"));
				list.add(fu);
			}
 
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList<FileUpload> requestuserdata(int id) {
		ArrayList<FileUpload> list = new ArrayList<FileUpload>();
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select * from request where user_id = '"+id+"' and status = 'active' ";
			rs = st.executeQuery(query);

			while(rs.next()) 
			{
				FileUpload fu = new FileUpload();
				
				fu.setId(rs.getInt("id"));
				fu.setUser_id(rs.getInt("user_id"));
				fu.setFilename(rs.getString("filename"));
				fu.setFiletype(rs.getString("filetype"));
				list.add(fu);
			}
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList<FileUpload> alldata1(String filename) {
		ArrayList<FileUpload> list = new ArrayList<FileUpload>();
		
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select id,filename,filetype,aboutfile,filekey from uploadfile where filename = '"+filename+"' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				
				FileUpload fu = new FileUpload();
				fu.setId(rs.getInt("id"));
				fu.setFilename(rs.getString("filename"));
				fu.setFiletype(rs.getString("filetype"));
				fu.setAboutfile(rs.getString("aboutfile"));
				list.add(fu);
			}
 
			st.close();
			con.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean deleter(int id) {
		boolean flag = false;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "update request set status = 'Stop' where id = '"+id+"' ";
			st.executeUpdate(query);
			flag = true;
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public ArrayList<FileUpload> customRequest(int user_id) {
		
		ArrayList<FileUpload> list = new ArrayList<FileUpload>();
		
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select * from uploadfile where user_id='"+user_id+"' and status = 'active' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				FileUpload fu = new FileUpload();
				fu.setId(rs.getInt("id"));
				fu.setUser_id(rs.getInt("user_id"));
				fu.setFilename(rs.getString("filename"));
				fu.setFiletype(rs.getString("filetype"));
				list.add(fu);
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String uname(int user_id) {
		String uname=null;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select firstname from registration where id = '"+user_id+"' ";
			rs = st.executeQuery(query);

			while(rs.next()) 
			{
				
			/*User bean=new User();
			bean.setF_name(rs.getString("firstname"));
			uname=bean.getF_name();
			*/
			uname=rs.getString("firstname");
				
			}
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uname;
	}

	@Override
	public int userId(String filename) {
		int user_id = 0;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select user_id from request where filename = '"+filename+"' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				
			/*User bean=new User();
			bean.setF_name(rs.getString("firstname"));
			uname=bean.getF_name();
			*/
			user_id=rs.getInt("user_id");
			}
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user_id;
	}

	@Override
	public ArrayList<Contact> cdata() {
		ArrayList<Contact> list = new ArrayList<Contact>();
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select * from contact";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				Contact fu = new Contact();
				
				fu.setId(rs.getInt("id"));
				fu.setUser_id(rs.getInt("user_id"));
				fu.setName(rs.getString("name"));
				fu.setMail(rs.getString("mail"));
				fu.setMessage(rs.getString("message"));
				
				list.add(fu);
			}
 
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public User userdata(String firstname) {
		User fu = new User();
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select lastname,username,phoneno,email from registration where firstname = '"+firstname+"'";
			rs = st.executeQuery(query);
		
			while(rs.next()) 
			{
				fu.setL_name(rs.getString("lastname"));
				fu.setU_name(rs.getString("username"));
				fu.setPhoneno(rs.getString("phoneno"));
				fu.setEmail(rs.getString("email"));
				System.out.println(fu.getEmail());
			}
 
			st.close();
			con.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fu;
	}

	@Override
	public int countrequest() {
		int i = 0;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select count(id) from request ";
			rs = st.executeQuery(query);
			
			if(rs.next())
			{
				i = rs.getInt(1);
			}
			
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int countcontact() {
		int i = 0;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select count(id) from contact ";
			rs = st.executeQuery(query);
			
			if(rs.next())
			{
				i = rs.getInt(1);
			}
			
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public boolean changeStatus(String email) {
		boolean flag = false;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "update registration set status = 'active' where email = '"+email+"' ";
			st.executeUpdate(query);
			
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public User emailObject(User ur) {
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select username from registration where email = '"+ur.getEmail()+"' ";
			rs = st.executeQuery(query);
			
			if(rs.next())
			{
				ur.setU_name(rs.getString("username"));
				return ur;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ur;
	}

	@Override
	public boolean changePassword(String email, String pass) {
		boolean flag = false;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "update registration set password = '"+pass+"' where email = '"+email+"' ";
			st.executeUpdate(query);
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public String getkey(String filename) {
		String getkey=null;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select filekey from uploadfile where filename = '"+filename+"' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				getkey=rs.getString("filekey");
			}
			
			rs.close();
			st.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getkey;
	}

	@Override
	public String getemail(String name) {
		String getemail=null;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select email from registration where firstname = '"+name+"' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				getemail=rs.getString("email");
			}
			
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getemail;
	}

	@Override
	public boolean changeCount(String filename) {
		boolean flag = false;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "update request set count = '1' where filename = '"+filename+"' ";
			st.executeUpdate(query);
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean changeCountKey(String filename) {
		boolean flag = false;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "update request set count = '0' where filename = '"+filename+"' ";
			st.executeUpdate(query);
			
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public ArrayList<FileUpload> Request(int id) {
		ArrayList<FileUpload> list = new ArrayList<FileUpload>();
		
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			query = "select * from request where user_idupload='"+id+"' and status = 'active' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				
				FileUpload fu = new FileUpload();
				fu.setId(rs.getInt("id"));
				fu.setUser_id(rs.getInt("user_id"));
				fu.setUser_idupload(rs.getInt("user_idupload"));
				fu.setFilename(rs.getString("filename"));
				fu.setFiletype(rs.getString("filetype"));
				list.add(fu);
				
			}
			rs.close();
			st.close();
			con.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public int getid(String filename) {
		int getid = 0;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select user_id from uploadfile where filename = '"+filename+"' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				getid = rs.getInt("user_id");
			}
			
			rs.close();
			st.close();
			con.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return getid;
	}

	@Override
	public int userIdupload(int id) {
		int userIdupload = 0;
		try {
			con = DBConnection.getConnection();
			st = con.createStatement();
			
			query = "select user_idupload from request where user_id = '"+id+"' ";
			rs = st.executeQuery(query);
			
			while(rs.next()) 
			{
				userIdupload = rs.getInt("user_idupload");
			}
			
			rs.close();
			st.close();
			con.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return userIdupload;
	}
}
