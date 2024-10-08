package com.springBootBoard_PersonalEdition.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.springBootBoard_PersonalEdition.DataNotFoundException;
import com.springBootBoard_PersonalEdition.answer.Answer;
import com.springBootBoard_PersonalEdition.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepository;

	public Specification<Question> search(String keyWord) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				query.distinct(true);
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				
				return cb.or(
					cb.like(q.get("subject"), "%" + keyWord + "%"),
					cb.like(q.get("content"), "%" + keyWord + "%"),
					cb.like(u1.get("username"), "%" + keyWord + "%"),
					cb.like(a.get("content"), "%" + keyWord + "%"),
					cb.like(u2.get("username"), "%" + keyWord + "%")
				);
			}
		};
	}
	
	public List<Question> getList() {
		
		return this.questionRepository.findAll();
	}
	
	public Page<Question> getListByAuthor(SiteUser author, int page) {

		Pageable pageable = PageRequest.of(page, 10);
		return this.questionRepository.findAllByAuthorOrderByCreateDateDesc(author, pageable);
	}

	public Question getQuestion(Integer id) {

		Optional<Question> question = this.questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public void create(String subject, String content, SiteUser author) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		question.setAuthor(author);
		this.questionRepository.save(question);
	}

	public Page<Question> getList(int page, String keyWord) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.questionRepository.findAllByKeyword(keyWord, pageable);
	}

	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}

	public void delete(Question question) {
		this.questionRepository.delete(question);
	}

	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
}
