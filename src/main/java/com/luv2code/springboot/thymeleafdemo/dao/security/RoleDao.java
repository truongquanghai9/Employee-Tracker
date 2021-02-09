package com.luv2code.springboot.thymeleafdemo.dao.security;

import com.luv2code.springboot.thymeleafdemo.entity.security.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
