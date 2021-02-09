package com.luv2code.springboot.thymeleafdemo.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.luv2code.springboot.thymeleafdemo.validate.FieldMatch;
import com.luv2code.springboot.thymeleafdemo.validate.ValidEmail;

@FieldMatch.List({
			@FieldMatch(first="password", second="matchingPassword", message="Password does not match")
})
public class CrmUser {

	@NotNull(message="is required")
	@Size(min=1, message = "is required")
	private String username;

	@NotNull(message="is required")
	@Size(min=1, message = "is required")
	private String password;
	
	@NotNull(message="is required")
	@Size(min=1, message = "is required")
	private String matchingPassword;
	
	@NotNull(message="is required")
	@Size(min=1, message = "is required")
	private String firstName;
	
	@NotNull(message="is required")
	@Size(min=1, message = "is required")
	private String lastName;
	
	@NotNull(message="Email is required")
	@Size(min=1, message = "is required")
	@ValidEmail
	private String email;
	
	private String formRole;
	
	public CrmUser() {}
	
	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFormRole() {
		return formRole;
	}

	public void setFormRole(String formRole) {
		this.formRole = formRole;
	}
	
	
}
