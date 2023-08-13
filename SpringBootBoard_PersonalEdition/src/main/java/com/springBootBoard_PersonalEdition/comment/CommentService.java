package com.springBootBoard_PersonalEdition.comment;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBootBoard_PersonalEdition.DataNotFoundException;
import com.springBootBoard_PersonalEdition.answer.Answer;
import com.springBootBoard_PersonalEdition.question.Question;
import com.springBootBoard_PersonalEdition.user.SiteUser;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	public Comment getComment(Integer id) {
		Optional<Comment> comment = commentRepository.findById(id);
		if (comment.isPresent()) {
			return comment.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public Comment create(Question question, SiteUser author, String content) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setAuthor(author);
		comment.setQuestion(question);
		comment.setCreateDate(LocalDateTime.now());
		comment = this.commentRepository.save(comment);

		return comment;
	}

	public Comment create(Answer answer, SiteUser author, String content) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setAuthor(author);
		comment.setAnswer(answer);
		comment.setCreateDate(LocalDateTime.now());
		comment = this.commentRepository.save(comment);

		return comment;
	}

	public Comment modify(Comment comment, String content) {
		comment.setContent(content);
		comment.setModifyDate(LocalDateTime.now());
		comment = this.commentRepository.save(comment);

		return comment;
	}

	public void delete(Comment comment) {
		this.commentRepository.delete(comment);
	}
}
