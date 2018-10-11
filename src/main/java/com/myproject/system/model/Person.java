package com.myproject.system.model;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int sex;
	private String description;
	private Date createDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", sex=" + sex + ", description=" + description + ", createDate="
				+ createDate + "]";
	}

	
}
