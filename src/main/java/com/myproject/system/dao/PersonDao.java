package com.myproject.system.dao;

import java.util.List;

import com.myproject.system.model.Person;

public interface PersonDao {
	
	public List<Person> selectAllPerson();
	public void insertPerson(Person person);
	
}
