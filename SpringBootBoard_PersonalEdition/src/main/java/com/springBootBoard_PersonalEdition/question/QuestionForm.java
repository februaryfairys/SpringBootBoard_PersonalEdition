package com.springBootBoard_PersonalEdition.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

	@Size
	@NotEmpty(message="please enter the subject.")
	private String subject;
	
	@NotEmpty(message="please enter the content.")
	private String content;
}
