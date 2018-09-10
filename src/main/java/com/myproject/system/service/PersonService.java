package com.myproject.system.service;

import java.util.List;

import com.myproject.system.model.Person;

public interface PersonService {
	
	public List<Person> selectAllPerson();
	public void insertPerson(Person person);
}
