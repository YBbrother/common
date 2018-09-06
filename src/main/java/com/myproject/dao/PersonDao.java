package com.myproject.dao;

import java.util.List;

import com.myproject.model.Person;

public interface PersonDao {
	
	public List<Person> selectAllPerson();
	public void insertPerson(Person person);
	
}
