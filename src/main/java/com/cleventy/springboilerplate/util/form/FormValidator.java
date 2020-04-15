package com.cleventy.springboilerplate.util.form;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import com.cleventy.springboilerplate.business.services.exceptions.BadRequestException;
import com.cleventy.springboilerplate.web.controller.api.form.GenericForm;


public class FormValidator {

	public static void validate(GenericForm genericForm) throws BadRequestException {
		Set<ConstraintViolation<GenericForm>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(genericForm);
		if (!violations.isEmpty()) {
			ConstraintViolation<GenericForm> violation = violations.iterator().next();
			throw new BadRequestException(violation.getPropertyPath(), violation.getMessage());
		}
	}
	public static void checkPasswords(String newPassword, String newPasswordConfirmation) throws BadRequestException {
		if (!newPassword.equals(newPasswordConfirmation)) {
			throw new BadRequestException("newPassword", "New password and new password confirmation doesn't match");
		}
	}


}
