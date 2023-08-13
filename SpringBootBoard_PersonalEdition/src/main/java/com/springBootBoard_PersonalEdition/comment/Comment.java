package com.springBootBoard_PersonalEdition.comment;

import java.time.LocalDateTime;

import com.springBootBoard_PersonalEdition.answer.Answer;
import com.springBootBoard_PersonalEdition.question.Question;
import com.springBootBoard_PersonalEdition.user.SiteUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private SiteUser author;

	private String content;

	private LocalDateTime createDate;

	private LocalDateTime modifyDate;

	@ManyToOne
	private Question question;

	@ManyToOne
	private Answer answer;

	public Integer getQuestionId() {
		Integer result = null;

		if (this.question != null) {
			result = this.question.getId();
		} else if (this.answer != null) {
			result = this.answer.getQuestion().getId();
		}
		return result;
	}
}
