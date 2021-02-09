package com.luv2code.springboot.thymeleafdemo.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.dao.security.RoleDao;
import com.luv2code.springboot.thymeleafdemo.dao.security.UserRepository;
import com.luv2code.springboot.thymeleafdemo.entity.security.Role;
import com.luv2code.springboot.thymeleafdemo.entity.security.User;
import com.luv2code.springboot.thymeleafdemo.user.CrmUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public User findByUserName(String userName) {
		return userRepository.getUserByUsername(userName);
	}

	@Override
	@Transactional
	public void save(CrmUser crmUser) {
		User user = new User();
		user.setUsername(crmUser.getUserName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());

		if (crmUser.getFormRole().equals("ROLE_EMPLOYEE")) {
			user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
		}
		else {
			user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE"),roleDao.findRoleByName(crmUser.getFormRole())));
		}
		 // save user in the database
		user = userRepository.saveAndFlush(user);
	}



}
