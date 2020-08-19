package com.des.bean;

public class FileUpload {

	int id,user_id,user_idupload;
	public int getUser_idupload() {
		return user_idupload;
	}
	public void setUser_idupload(int user_idupload) {
		this.user_idupload = user_idupload;
	}
	String filename,filetype,fileupload,aboutfile,date,size,key_gen,previlage,enctime,search;
	byte[] key;
	

	public byte[] getKey() {
		return key;
	}
	public void setKey(byte[] key) {
		this.key = key;
	}
	public String getAboutfile() {
		return aboutfile;
	}
	public void setAboutfile(String aboutfile) {
		this.aboutfile = aboutfile;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getKey_gen() {
		return key_gen;
	}
	public void setKey_gen(String key_gen) {
		this.key_gen = key_gen;
	}
	public String getPrevilage() {
		return previlage;
	}
	public void setPrevilage(String previlage) {
		this.previlage = previlage;
	}
	public String getEnctime() {
		return enctime;
	}
	public void setEnctime(String enctime) {
		this.enctime = enctime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilename1() {
		return filename;
	}
	public void setFilename1(String filename1) {
		this.filename = filename;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getFileupload() {
		return fileupload;
	}
	public void setFileupload(String fileupload) {
		this.fileupload = fileupload;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
}
