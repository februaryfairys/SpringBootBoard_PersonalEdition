package com.springBootBoard_PersonalEdition.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {

	@NotEmpty(message = "Please enter the content")
	private String content;
}
