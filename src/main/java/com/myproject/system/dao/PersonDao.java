package com.myproject.system.dao;

import java.util.List;

import com.myproject.system.model.Person;

public interface PersonDao {
	
	List<Person> selectAllPerson();
	void insertPerson(Person person);
	
}
