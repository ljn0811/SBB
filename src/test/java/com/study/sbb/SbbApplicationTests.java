package com.study.sbb;

import static org.assertj.core.api.Assertions.registerCustomDateFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.sbb.answer.AnswerRepository;
import com.study.sbb.question.QuestionRepository;
import com.study.sbb.question.QuestionService;

import jakarta.transaction.Transactional;

@SpringBootTest //스프링 부트의 테스트 클래스임을 의미
class SbbApplicationTests {
	
	@Autowired //의존성 주입
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository; //레파지토리 주입
	
	@Autowired
	private QuestionService questionService;

	/*
	 * @Test void contextLoads() { }
	 */
	
//	@Test 	//testJpa 메서드가 테스트 메서드임을 나타냄.
//	void testJpa() {		
//		
//		 Question q1 = new Question(); //질문 엔티티 객체 생성 q1.setSubject("sbb가 무엇인가요?");
//		 q1.setContent("sbb에 대해서 알고 싶습니다."); 
//		 q1.setCreateDate(LocalDateTime.now());
//		 this.questionRepository.save(q1); //첫번째 질문 저장
//		  
//		 Question q2 = new Question(); //질문 엔티티 객체 생성
//		 q2.setSubject("스프링부트 모델 질문입니다."); 
//		 q2.setContent("id는 자동으로 생성되나요?");
//		 q2.setCreateDate(LocalDateTime.now()); 
//		 this.questionRepository.save(q2); //두번째 질문 저장		 		
//		
//	}
//	
//	@Test
//	void testJpa2() {
//		List<Question> all = this.questionRepository.findAll(); //findAll: 테이블에 저장된 모든 데이터 조회하기 위한 메서드
//		assertEquals(2, all.size()); //assertEquals(기대값, 실제값) >> 기대값과 실제값이 동일한지 조사 동일하지 않으면 실패 처리.
//		
//		Question q = all.get(0);
//		assertEquals("sbb가 무엇인가요? ", q.getSubject());
//	}
//	
//	@Test
//	void testJpa3() {
//		Optional<Question> oq = this.questionRepository.findById(1); //findById: id 값으로 데이터 조회. 리턴 타입은 Optional
//		if(oq.isPresent()) { //isPresent() 메서드로 값이 존재하는지 확인 가능
//			Question q = oq.get(); //값 존재 시 get() 메서드 통해 실제 Question 객체의 값을 얻음.
//			assertEquals("sbb가 무엇인가요", q.getSubject());
//			//결론: id 값이 1인 질문 검색 후 해당 질문의 제목이 sbb가 무엇인가요인 경우 JUnit 테스트 통과
//		}
//	}
	
//	@Test
//	void testJpa4() {
//		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요"); //findBy + 엔티티 속성명과 같은 리포지터리 메서드 작성 시 입력한 속성의 값으로 데이터 조회 가능
//		assertEquals(1, q.getId()); 콘솔 창 쿼리문 확인
//	}
	
//	@Test
//	void testJpa5() {
//		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요", "sbb에 대해서 알고 싶습니다.");
//		assertEquals(1, q.getId()); //콘솔 창 쿼리문 확인
//	}
	
//	@Test
//	void testJpa6() {
//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%"); //데이터 조회 위한 조건이 되는 문자열
//		Question q = qList.get(0);
//		assertEquals("sbb가 무엇인가요? " + q.getSubject());
//	}

//	@Test
//	void testJpa() {
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent()); //괄호 안의 값이 참인지 확인. false 리턴 시 오류 발생
//		Question q = oq.get(); //데이터 조회
//		q.setSubject("수정된 제목"); //데이터 수정
//		this.questionRepository.save(q); //변경된 질문을 데이터베이스에 저장
//		//콘솔에 update 문 실행
//	}
	
//	@Test
//	void testJpa() {
//		assertEquals(2, this.questionRepository.count()); //삭제 전 데이터 2건
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		this.questionRepository.delete(q); //q 삭제
//		assertEquals(1, this.questionRepository.count()); //count: 테이블 행의 개수 리턴 삭제 후 데이터 1건
//	}
	
//	@Test
//	void testJpa() {
//		Optional<Question> oq = this.questionRepository.findById(2); //답변을 생성하려면 질문이 필요함. 질문 조회. id 2인 질문 데이터 가져옴
//		assertTrue(oq.isPresent()); 
//		Question q = oq.get();
//		
//		Answer a = new Answer();
//		a.setContent("네 자동으로 생성됩니다."); //답변의 question 속성에 대입
//		a.setQuestion(q); //어떤 질문의 답변인지 알기 위해 Question 객체 필요
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);
//	}
	
//	@Test
//	void testJpa() {
//		Optional<Answer> oa = this.answerRepository.findById(1); //id가 1인 답변 조회
//		assertTrue(oa.isPresent());
//		Answer a = oa.get();
//		assertEquals(2, a.getQuestion().getId()); //조회한 답변과 연결된 질문의 id가 2인지도 조회 a.getQuestion() >> 답변에 연결된 질문에 접근 가능
//	}
	
//	@Transactional //메서드가 종료될 때까지 DB 세션 유지
//	@Test
//	void testJpa() {
//		Optional<Question> oq = this.questionRepository.findById(2); //질문 조회. 여기서 DB 세션 종료 >> 테스트 수행 시 오류 발생 >> 방지 위해 Transactional 어노테이션 사용
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		
//		List<Answer> answerList = q.getAnswerList(); 
//		
//		assertEqauls(1, answerList.size()); //질문에 달린 답변 전체 구함
//		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
//	}
	
	/* 대량 데이터 생성 */
//	@Test
//	void testJpa() {
//		for(int i=1; i<=300; i++) {
//			String subject = String.format("테스트 데이터입니다. : [%30d]", i);
//			String content = "내용무";
//			this.questionService.create(subject, content);
//		}
//	}
	
	@Test
	void testJpa() {
		for(int i=1; i<=300; i++) {
			String subject = String.format("테스트 데이터입니다. : [%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}
	}
}
