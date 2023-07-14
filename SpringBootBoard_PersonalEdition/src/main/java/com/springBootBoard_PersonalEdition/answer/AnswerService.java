package com.springBootBoard_PersonalEdition.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.springBootBoard_PersonalEdition.question.Question;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

	private final AnswerRepository answerRepository;
	
	public void create(Question question, String content) {
		
		Answer answer = new Answer();
		
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		
		this.answerRepository.save(answer);
	}
}
