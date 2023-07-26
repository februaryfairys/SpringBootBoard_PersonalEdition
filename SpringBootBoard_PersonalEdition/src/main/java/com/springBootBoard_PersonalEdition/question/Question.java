package com.springBootBoard_PersonalEdition.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.springBootBoard_PersonalEdition.answer.Answer;
import com.springBootBoard_PersonalEdition.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;

	private LocalDateTime createDate;

	private LocalDateTime modifyDate;

	@ManyToOne
	private SiteUser author;

	@ManyToMany
	Set<SiteUser> voter;
	// To using Set to prevent duplication of voter.
}
