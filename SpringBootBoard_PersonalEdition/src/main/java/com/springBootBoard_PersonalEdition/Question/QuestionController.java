package com.springBootBoard_PersonalEdition.Question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springBootBoard_PersonalEdition.Answer.AnswerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

	private final QuestionService questionService;
	
	@GetMapping("/list")
	public String list(Model model) {
		
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		
		return "question_form";
	}
	
	
	/*questionCreate 메서드의 매개변수를 subject, content 대신 QuestionForm 객체로 변경했다.
	 *subject, content 항목을 지닌 폼이 전송되면 QuestionForm의 subject, content 속성이 자동으로 바인딩 된다. 
	 *이것은 스프링 프레임워크의 바인딩 기능이다.**/
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		this.questionService.create(questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/list";
	}
}
