package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luv2code.springboot.thymeleafdemo.entity.security.User;
import com.luv2code.springboot.thymeleafdemo.service.UserService;
import com.luv2code.springboot.thymeleafdemo.user.CrmUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	@Autowired
	private UserService userService;
	
	
	private Map<String, String> roles;
	
	@PostConstruct
	protected void loadRoles() {
		roles = new LinkedHashMap<String,String>();
		roles.put("ROLE_EMPLOYEE", "Employee");
		roles.put("ROLE_ADMIN", "Admin");
		roles.put("ROLE_MANAGER", "Manager");
	}
	
	
	@InitBinder
	public void initBiner(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/showRegistrationForm")
	public String showRegistrationForm(Model model) {
		model.addAttribute("crmUser",new CrmUser());
		model.addAttribute("roles", roles);
		return "registration-form";
	}
	
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute CrmUser crmUser, 
											 BindingResult bindingResult,
												Model model) {
		String userName = crmUser.getUserName();
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("roles",roles);
			return "registration-form";
		}
		
		User existingUser = userService.findByUserName(userName);
		if (existingUser != null) {
			model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("roles",roles);

			model.addAttribute("registrationError", "User name already exists.");
			return "registration-form";
		}
		
		userService.save(crmUser);
		
		return "registration-confirmation";
	}
}
