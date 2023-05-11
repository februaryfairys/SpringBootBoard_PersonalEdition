package com.springBootBoard_PersonalEdition.Question;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

	List<Question> findBySubject(String subject);

	List<Question> findBySubjectAndContent(String subject, String content);
	
	List<Question> findBySubjectOrContent(String subject, String content);
	
	List<Question> findByCreateDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
	
	List<Question> findByIdLessThan(Integer id);
	
	List<Question> findByIdGreaterThanEqual(Integer id);
	
	List<Question> findBySubjectLike(String subject);
	
	List<Question> findBySubjectIn(String[] subjects);
	
	List<Question> findBySubjectOrderByCreateDateAsc(String subject);
	
	List<Question> findBySubjectOrderByCreateDateDesc(String subject);
}
