package com.springBootBoard_PersonalEdition.Question;

import java.time.LocalDateTime;
import java.util.List;

import com.springBootBoard_PersonalEdition.Answer.Answer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private List <Answer> answerList;
	
	private LocalDateTime createDate;
}
