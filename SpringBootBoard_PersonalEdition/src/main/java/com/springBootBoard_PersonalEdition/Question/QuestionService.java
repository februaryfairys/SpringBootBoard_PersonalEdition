package com.springBootBoard_PersonalEdition.Question;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepository;
	
	public List<Question> getList() {
		
		return this.questionRepository.findAll();
	}
}
