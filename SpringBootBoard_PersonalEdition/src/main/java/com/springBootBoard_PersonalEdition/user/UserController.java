package com.springBootBoard_PersonalEdition.user;

import java.security.Principal;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springBootBoard_PersonalEdition.answer.AnswerService;
import com.springBootBoard_PersonalEdition.comment.CommentService;
import com.springBootBoard_PersonalEdition.question.Question;
import com.springBootBoard_PersonalEdition.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	private final QuestionService questionService;
	
	private final AnswerService answerService;
	
	private final CommentService commentService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "Passwords don't match");
			return "signup_form";
		}

		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		} catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "This ID or Email alreay used.");
			return "signup_form";
		} catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "This ID alreay used.");
			return "signup_form";
		}

		return "redirect:/";
	}
	
	@GetMapping("/signin")
	public String signin() {
		return "signin_form";
	}
	
	@GetMapping("/detail")
	@PreAuthorize("isAuthenticated()")
	public String detail(Model model, Principal principal, PasswordForm passwordForm) {
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		model.addAttribute("siteUser", siteUser);
		
		return "user_detail";
	}
	
	@PostMapping("/password/check")
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	public boolean isPasswordMatching(Principal principal, @RequestParam String password) {
		return this.userService.isPasswordMatching(principal, password);
	}
	
//	@GetMapping("/password/update")
//	@PreAuthorize("isAuthenticated()")
//	public boolean updatePassword(Model model, @Valid PasswordForm passwordForm, BindingResult bindingResult, Principal principal) {
//		
//		if (bindingResult.hasErrors()) {
//			return false;
//		}
//		
//		if (!passwordForm.getPassword1().equals(passwordForm.getPassword2())) {
//			bindingResult.rejectValue("password2", "passwordInCorrect", "New passwords don't match");
//			return false;
//		}
//		
//		userService.updatePassword(principal, passwordForm.getPassword1());
//		
//		return true;
//	
//	}
}