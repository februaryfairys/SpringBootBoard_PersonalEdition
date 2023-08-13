package com.springBootBoard_PersonalEdition.comment;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.springBootBoard_PersonalEdition.answer.Answer;
import com.springBootBoard_PersonalEdition.answer.AnswerService;
import com.springBootBoard_PersonalEdition.question.Question;
import com.springBootBoard_PersonalEdition.question.QuestionService;
import com.springBootBoard_PersonalEdition.user.SiteUser;
import com.springBootBoard_PersonalEdition.user.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService; 

	@Autowired
	private UserService userService;

	@GetMapping("/create/question/{id}")
	@PreAuthorize("isAuthenticated()")
	public String createQuestionComment(CommentForm commentForm) {
		return "comment_form";
	}

	@PostMapping("/create/question/{id}")
	@PreAuthorize("isAuthenticated()")
	public String createQuestionComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm,
			BindingResult bindingResult, Principal principal) {

		Question question = this.questionService.getQuestion(id);
		SiteUser user = this.userService.getUser(principal.getName());

		if (bindingResult.hasErrors()) {
			return "comment_form";
		}

		Comment comment = this.commentService.create(question, user, commentForm.getContent());

		return String.format("redirect:/question/detail/%s", comment.getQuestionId());
	}
	
	@GetMapping("/create/answer/{id}")
	@PreAuthorize("isAuthenticated()")
	public String createAnswerComment(CommentForm commentForm) {
		return "comment_form";
	}

	@PostMapping("/create/answer/{id}")
	@PreAuthorize("isAuthenticated()")
	public String createAnswerComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm,
			BindingResult bindingResult, Principal principal) {

		Answer answer = this.answerService.getAnswer(id);
		SiteUser user = this.userService.getUser(principal.getName());

		if (bindingResult.hasErrors()) {
			return "comment_form";
		}

		Comment comment = this.commentService.create(answer, user, commentForm.getContent());

		return String.format("redirect:/question/detail/%s", comment.getQuestionId());
	}

	@GetMapping("/modify/{id}")
	@PreAuthorize("isAuthenticated()")
	public String modifyComment(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {

		Comment comment = this.commentService.getComment(id);

		if (!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not authorized to edit.");
		}

		commentForm.setContent(comment.getContent());

		return "comment_form";
	}

	@PostMapping("/modify/{id}")
	@PreAuthorize("isAuthenticated()")
	public String modifyComment(CommentForm commentForm, BindingResult bindingResult, @PathVariable("id") Integer id,
			Principal principal) {

		if (bindingResult.hasErrors()) {
			return "comment_form";
		}

		Comment comment = this.commentService.getComment(id);

		if (!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not authorized to delete.");
		}

		this.commentService.modify(comment, commentForm.getContent());

		return String.format("redirect:/question/detail/%s", comment.getQuestionId());
	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("isAuthenticated()")
	public String deleteComment(Principal principal, @PathVariable("id") Integer id) {
		Comment comment = this.commentService.getComment(id);

		if (!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not authorized to delete.");
		}

		this.commentService.delete(comment);

		return String.format("redirect:/question/detail/%s", comment.getQuestionId());
	}
}
