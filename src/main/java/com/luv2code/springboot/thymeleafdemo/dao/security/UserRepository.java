package com.luv2code.springboot.thymeleafdemo.dao.security;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.security.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT tempUser FROM User tempUser where tempUser.userName=:username")
	public User getUserByUsername(@Param("username") String username);
}
