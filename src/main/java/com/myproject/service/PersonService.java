package com.myproject.service;

import java.util.List;

import com.myproject.entity.Person;

public interface PersonService {
	
	public List<Person> selectAllPerson();
	public void insertPerson(Person person);
}
