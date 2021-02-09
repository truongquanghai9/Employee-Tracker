package com.luv2code.springboot.thymeleafdemo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.luv2code.springboot.thymeleafdemo.entity.security.User;
import com.luv2code.springboot.thymeleafdemo.user.CrmUser;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);
	public void save(CrmUser crmUser);
}
