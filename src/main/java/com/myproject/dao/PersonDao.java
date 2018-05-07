package com.myproject.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.myproject.entity.Person;

@Repository
public interface PersonDao {
	
	public List<Person> selectAllPerson();
	
}
