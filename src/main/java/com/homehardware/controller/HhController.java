package com.homehardware.controller;

import com.homehardware.dao.HhDaoImpl;

import javax.persistence.TransactionRequiredException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class HhController {

	protected static final Logger logger = Logger.getLogger(HhController.class);

	@Autowired
	private HhDaoImpl hhDaoImpl;

	/**
	 * Return login screen.
	 * 
	 */	
	@RequestMapping("/login")
	public final String login() {
		/*try {
			final Employee employee = new Employee();
			hhDaoImpl.setEmployeeDetails(employee);
			hhDaoImpl.saveEmployee(employee);
		} catch (TransactionRequiredException e) {
			logger.error("Error while persisting " + e.getMessage());
		}*/
		return "login";
	}
	
	/**
	 * this methid returns login a store object.
	 * 
	 */	
	@RequestMapping("/addAttribute")
	public final String addAttribute(final ModelMap map) {
		try {
			
			//hhDaoImpl.saveItemDetails();;
			hhDaoImpl.getStore();
		} catch (TransactionRequiredException e) {
			logger.error("Error while persisting " + e.getCause());
		}
		return "home";
	}
	
	

}
