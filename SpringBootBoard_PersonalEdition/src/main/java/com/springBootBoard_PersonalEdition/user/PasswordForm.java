package com.springBootBoard_PersonalEdition.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForm {

	@NotEmpty(message = "Please enter the password.")
	private String currentPassword;
	
	@NotEmpty(message = "Please enter the password.")
	private String password1;
	
	@NotEmpty(message = "Please enter the password.")
	private String password2;
}
