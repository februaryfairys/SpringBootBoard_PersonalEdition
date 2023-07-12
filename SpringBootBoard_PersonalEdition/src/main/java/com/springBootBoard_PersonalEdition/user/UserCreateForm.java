package com.springBootBoard_PersonalEdition.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

	@Size(min = 3, max = 25, message = "Please check the form.")
	@NotEmpty(message = "Please enter the name.")
	private String username;
	
	@NotEmpty(message = "Please enter the password.")
	private String password1;
	
	@NotEmpty(message = "Please enter the password.")
	private String password2;
	
	@Email
	@NotEmpty(message = "Please enter the correct email.")
	private String email;
}
