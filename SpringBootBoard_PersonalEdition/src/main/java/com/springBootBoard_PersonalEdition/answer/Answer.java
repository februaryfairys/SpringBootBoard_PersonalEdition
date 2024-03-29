package com.springBootBoard_PersonalEdition.answer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.springBootBoard_PersonalEdition.comment.Comment;
import com.springBootBoard_PersonalEdition.question.Question;
import com.springBootBoard_PersonalEdition.user.SiteUser;

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
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Question question;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createDate;

	private LocalDateTime modifyDate;

	@ManyToOne
	private SiteUser author;

	@ManyToMany
	Set<SiteUser> voter;
	// To using Set to prevent duplication of voter.
	
	@OneToMany(mappedBy = "answer")
	private List<Comment> commentList;

}
