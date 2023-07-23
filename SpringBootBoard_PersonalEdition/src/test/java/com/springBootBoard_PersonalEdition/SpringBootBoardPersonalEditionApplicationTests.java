package com.springBootBoard_PersonalEdition;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springBootBoard_PersonalEdition.question.QuestionRepository;
import com.springBootBoard_PersonalEdition.question.QuestionService;

@SpringBootTest
class SpringBootBoardPersonalEditionApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionService questionService;
	
//	@Autowired
//	private AnswerRepository answerRepository;
	
//	@Test
//	void testJpa() {
//		Question q1 = new Question();
//		q1.setSubject("Test Subject 01");
//		q1.setContent("Test Content 01");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1);
//		
//		Question q2 = new Question();
//		q2.setSubject("Test subject 02");
//		q2.setContent("Test content 02");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2);
//	}
	
//	@Test
//	void testJpa() {
//		List<Question> all = this.questionRepository.findAll();
//		assertEquals(2, all.size());
//		
//		Question q = all.get(0);
//		assertEquals("Test Subject 01", q.getSubject());
//	}
	
//	@Test
//	void testJpa() {
//		Optional<Question> oq = this.questionRepository.findById(1);
//		
//		if(oq.isPresent()) {
//			Question q = oq.get();
//			assertEquals("Test Subject 01",q.getSubject());
//		}
//	}

//	@Test
//	void testJpa() {
//		List<Question> question = this.questionRepository.findBySubjectAndContent("Test Subject 01", "Test Content 01");
//		assertEquals(1, question.get(0).getId());
//	}
	
//	@Test
//	void testJpa() {
//		List<Question> question = this.questionRepository.findBySubjectLike("%Test Subject 01%");
//		assertEquals(1, question.get(0).getId());
//		assertEquals("Test Content 01", question.get(0).getContent());
//	}
	
//	@Test
//	void testJpa() {
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		
//		Question question = oq.get();
//		assertEquals("Test content 02", question.getContent());
//		
//		question.setSubject("Test Subject #02");
//		question.setContent("Test Content #02");
//		this.questionRepository.save(question);
//	}
	
//	@Test
//	void testJpa() {
//		List<Question> question = this.questionRepository.findBySubject("Test Subject #01");
//		
//		assertEquals("Test Content #01", question.get(0).getContent());
//	}
	
//	@Test
//	void testJpa() {
//		Question q1 = new Question();
//		q1.setSubject("Test Subject #03");
//		q1.setContent("Test Content #03");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1);
//	}
	
//	@Test
//	void testJpa() {
//		assertEquals(3,this.questionRepository.count());
//		
//		Optional<Question> oq = this.questionRepository.findById(3);
//		assertTrue(oq.isPresent());
//		Question question = oq.get();
//		this.questionRepository.delete(question);
//		
//		oq = this.questionRepository.findById(3);
//		assertTrue(oq.isEmpty());
//	}
	
//	@Test
//	void testJpa() {
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question question = oq.get();
//		
//		Answer answer = new Answer();
//		answer.setQuestion(question);
//		answer.setContent("Test Answer #01");
//		answer.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(answer);
//	}
	
//	@Test
//	void testJpa() {
//		Optional<Answer> oa = this.answerRepository.findById(1);
//		assertTrue(oa.isPresent());
//		Answer answer = oa.get();
//		assertEquals("Test Answer #01", answer.getContent());
//	}
	
//	@Test
//	@Transactional
//	void testJpa() {
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question question = oq.get();
//		
//		List<Answer> answerList = question.getAnswerList();
//		
//		assertEquals(1, answerList.size());
//		assertEquals("Test Answer #01", answerList.get(0).getContent());
//	}
	@Test
	void testJpa() {
		
		for(int i = 5; i<= 300; i++) {
			String subject = String.format("Test Subject #%03d", i);
			String content = String.format("Test Content #%03d", i);
			
			this.questionService.create(subject, content, null);
		}
	}
}
