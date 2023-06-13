package com.springBootBoard_PersonalEdition.Answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

	@NotEmpty(message= "please enter the content.")
	private String content;
}
