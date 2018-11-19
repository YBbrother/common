package com.myproject.system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.system.model.Friend;
import com.myproject.system.model.Hobby;
import com.myproject.system.model.Person;
import com.myproject.system.service.friendservice.FriendService;
import com.myproject.system.service.hobbyservice.HobbyService;
import com.myproject.system.service.personservice.PersonService;
import com.myproject.system.service.timejob.TimeJob;

@Controller
@RequestMapping("sun")
public class PersonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	public PersonService personService;
	@Autowired 
	public FriendService friendService;
	@Autowired 
	public HobbyService hobbyService;
	@Autowired 
	public TimeJob timeJob;
	
	@RequestMapping("test")
	public ModelAndView test() {
		logger.info("生与死，轮回不止，我们生，他们死");
		logger.error("fhwiofwofhwofho");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("show");
		return mav;
	}
	
	@RequestMapping("person")
	public ModelAndView testPerson() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("aa");
		logger.info("******************************测试数据开始******************************");
		try {
			List<Person> allPerson = personService.selectAllPerson();
			for(Person person : allPerson) {
				System.out.println(person);
			}
			mav.addObject("persons", allPerson);
		}catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("******************************测试数据结束******************************");
		return mav;
	}
	
	@RequestMapping("transa")
	public void Transact() {
		try {
			Person person = new Person();
			person.setName("Jace");
			person.setDescription("handsome");
			
			Friend friend = new Friend();
			friend.setFname("Lucy");
			friend.setFsex(1);
			
			Hobby hobby = new Hobby();
			hobby.setHname("badminton");
			hobby.setHplace("sport");
			
			personService.insertPerson(person);
			friendService.insertFriend(friend);
			hobbyService.insertHobby(hobby);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
}
