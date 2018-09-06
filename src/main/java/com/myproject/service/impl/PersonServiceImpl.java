package com.myproject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.dao.PersonDao;
import com.myproject.model.Person;
import com.myproject.service.PersonService;

@Service("PersonService")
public class PersonServiceImpl implements PersonService {

	@Autowired 
	public PersonDao personDao;
	
	@Override
	public List<Person> selectAllPerson() {
		return personDao.selectAllPerson();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertPerson(Person person) {
		personDao.insertPerson(person);
	}

}
