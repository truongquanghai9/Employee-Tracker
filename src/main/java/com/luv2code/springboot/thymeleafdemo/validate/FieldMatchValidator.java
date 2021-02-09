package com.luv2code.springboot.thymeleafdemo.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	private String password;
	private String matchingPassword;
	private String message;
	
	@Override
	public void initialize(final FieldMatch fieldMatch) {
		password = fieldMatch.first();
		matchingPassword = fieldMatch.second();
		message = fieldMatch.message();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = true;
		
		try {
			final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(password);
			final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(matchingPassword);
			
			valid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
		}
		catch(Exception ignore) { }
		
		if (!valid) {
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode(password)
					.addConstraintViolation()
					.disableDefaultConstraintViolation();
		}
		
		return valid;
	}
	
}
