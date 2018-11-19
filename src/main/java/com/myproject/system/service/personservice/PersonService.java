package com.myproject.system.service.personservice;

import java.util.List;

import com.myproject.system.model.Person;

public interface PersonService {
	
	List<Person> selectAllPerson();
	void insertPerson(Person person);
}
