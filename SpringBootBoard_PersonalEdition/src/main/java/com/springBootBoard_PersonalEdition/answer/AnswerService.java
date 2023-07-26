package com.springBootBoard_PersonalEdition.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springBootBoard_PersonalEdition.DataNotFoundException;
import com.springBootBoard_PersonalEdition.question.Question;
import com.springBootBoard_PersonalEdition.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

	private final AnswerRepository answerRepository;

	public void create(Question question, String content, SiteUser author) {

		Answer answer = new Answer();

		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);

		this.answerRepository.save(answer);
	}

	public Answer getAnswer(Integer id) {

		Optional<Answer> answer = this.answerRepository.findById(id);

		if (answer.isPresent()) {
			return answer.get();
		} else {
			throw new DataNotFoundException("Answer not found.");
		}
	}

	public void modify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}

	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}

	public void vote(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser);
		this.answerRepository.save(answer);
	}
}
