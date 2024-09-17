package com.springBootBoard_PersonalEdition.question;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springBootBoard_PersonalEdition.user.SiteUser;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

	List<Question> findBySubject(String subject);

	List<Question> findBySubjectAndContent(String subject, String content);
	
	List<Question> findBySubjectOrContent(String subject, String content);
	
	List<Question> findByCreateDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
	
	Page<Question> findAllByAuthorOrderByCreateDateDesc(SiteUser author, Pageable pageable);
	
	List<Question> findByIdLessThan(Integer id);
	
	List<Question> findByIdGreaterThanEqual(Integer id);
	
	List<Question> findBySubjectLike(String subject);
	
	List<Question> findBySubjectIn(String[] subjects);
	
	List<Question> findBySubjectOrderByCreateDateAsc(String subject);
	
	List<Question> findBySubjectOrderByCreateDateDesc(String subject);
	
	Page<Question> findAll(Pageable pageable);
	
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	@Query("SELECT "
		+ " DISTINCT q "
		+ " FROM Question q "
		+ " LEFT OUTER JOIN SiteUser u1 ON q.author   = u1 "
		+ " LEFT OUTER JOIN Answer   a  ON a.question = q "
		+ " LEFT OUTER JOIN SiteUser u2 ON a.author   = u2 "
		+ " WHERE "
		+ " 	q.subject   LIKE %:kw% OR "
		+ " 	q.content   LIKE %:kw% OR "
		+ " 	a.content   LIKE %:kw% OR "
		+ " 	u1.username LIKE %:kw% OR "
		+ " 	u2.username LIKE %:kw% ")
	Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}