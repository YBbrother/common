package com.myproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.entity.Person;
import com.myproject.service.PersonService;

@Controller
@RequestMapping("sun")
public class PersonController {
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired 
	public PersonService personService;
	
	@RequestMapping("test")
	public ModelAndView test() {
		logger.info("生与死，轮回不止，我们生，他们死");
		logger.error("fhwiofwofhwofho");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("show");
		return mav;
	}
	
	@RequestMapping("person")
	public void testPerson() {
		logger.info("******************************测试数据开始******************************");
		try {
			List<Person> allPerson = personService.selectAllPerson();
			for(Person person : allPerson) {
				System.out.print(person.getId());
				System.out.print(person.getName());
				System.out.print(person.getSex());
				System.out.print(person.getDescription());
				System.out.println(person.getCreateDate());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("******************************测试数据结束******************************");
	}

}
