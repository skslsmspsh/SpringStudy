package com.test.domain;

import java.sql.Date;

public class UserVO {
	private int id;
	private String username;
	private String email;
	private String nickname;
	private String sns_id;
	private String sns_type;
	private String sns_profile;
	private Date create_date;
	private Date modify_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSns_id() {
		return sns_id;
	}
	public void setSns_id(String sns_id) {
		this.sns_id = sns_id;
	}
	public String getSns_type() {
		return sns_type;
	}
	public void setSns_type(String sns_type) {
		this.sns_type = sns_type;
	}
	public String getSns_profile() {
		return sns_profile;
	}
	public void setSns_profile(String sns_profile) {
		this.sns_profile = sns_profile;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	
	public String toString() {
	      return "UserVO [id="+id+",username="+username+",email="+email+",nickname="+nickname+",sns_id="+sns_id+",sns_type="+sns_type+",sns_profile="+sns_profile+",create_date="+create_date+",modify_date="+modify_date+"]";
	   }
	
}
