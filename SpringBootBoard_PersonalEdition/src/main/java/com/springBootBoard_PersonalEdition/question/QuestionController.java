package com.springBootBoard_PersonalEdition.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.springBootBoard_PersonalEdition.answer.AnswerForm;
import com.springBootBoard_PersonalEdition.user.SiteUser;
import com.springBootBoard_PersonalEdition.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

	private final QuestionService questionService;
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "keyWord", defaultValue = "") String keyWord) {

//		List<Question> questionList = this.questionService.getList();
//		model.addAttribute("questionList", questionList);
		Page<Question> paging = this.questionService.getList(page, keyWord);
		model.addAttribute("paging", paging);
		model.addAttribute("keyWord", keyWord);
		return "question_list";
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}

	@GetMapping("/create")
	@PreAuthorize("isAuthenticated()")
	public String questionCreate(QuestionForm questionForm) {

		return "question_form";
	}

	/*
	 * questionCreate 메서드의 매개변수를 subject, content 대신 QuestionForm 객체로 변경했다. subject,
	 * content 항목을 지닌 폼이 전송되면 QuestionForm의 subject, content 속성이 자동으로 바인딩 된다. 이것은
	 * 스프링 프레임워크의 바인딩 기능이다.
	 **/
	@PostMapping("/create")
	@PreAuthorize("isAuthenticated()") /* 반드시 로그인이 필요한 메서드에는 본 애너테이션이 붙는다. Security Config와 함께 연계 */
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}

		SiteUser siteUser = userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}

	@GetMapping("/modify/{id}")
	@PreAuthorize("isAuthenticated()")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {

		Question question = this.questionService.getQuestion(id);

		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not authorized to edit.");
		}

		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}

	@PostMapping("/modify/{id}")
	@PreAuthorize("isAuthenticated()")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal,
			@PathVariable("id") Integer id) {

		if (bindingResult.hasErrors()) {
			return "question_form";
		}

		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not authorized to edit.");
		}

		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		return String.format("redirect:/question/detail/%s", id);
	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("isAuthenticated()")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {

		Question question = this.questionService.getQuestion(id);

		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not authorized to delete.");
		}
		
		this.questionService.delete(question);
		return "redirect:/";
	}
	
	@GetMapping("/vote/{id}")
	@PreAuthorize("isAuthenticated()")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		this.questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
}
