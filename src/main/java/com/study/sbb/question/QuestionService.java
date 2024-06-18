package com.study.sbb.question;

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

import com.study.sbb.DataNotFoundException;
import com.study.sbb.answer.Answer;
import com.study.sbb.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	//@RequiredArgsConstructor에 의해 생성자 방식으로 주입됨.
	private final QuestionRepository questionRepository;	
	
	@SuppressWarnings("unused")
	private Specification<Question> search(String kw) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				//q: Root 자료형. 기준이 되는 Question 엔티티의 객체를 의미. 질문 제목과 내용 검색 위해 필요
				//u1: Question 엔티티와 SiteUser 엔티티를 아우터 조인하여 만든 SiteUser 엔티티 객체. u1 객체는 질문 작성자를 검색하기 위해 필요.
				//a: Question 엔티티와 Answer 엔티티를 아우터 조인하여 만든 Answer 엔티티의 객체
				//u2: 바로 앞에 작성한 a 객체와 다시 한번 SiteUser 엔티티와 아우터 조인하여 만든 SiteUser 엔티티의 객체로 답변 작성자를 검색할 때 필요
				query.distinct(true); // 중복을 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
						cb.like(q.get("content"), "%" + kw + "%"), // 내용
						cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
						cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
			}
		};
	}
	
	//목록
	public Page<Question> getList(int page, String kw){
		//리스트 출력 시 페이징 기능 추가
		//getList 메서드는 정수 타입 페이지 번호 입력받아 해당 페이지의 Page 객체를 리턴
		//PageRequest.of(page, 10): page >> 조회할 페이지 번호. 10은 한 페이지에 보여 줄 게시물의 개수
		//데이터 전체를 조회하지 않고 해당 페이지의 데이터만 조회하도록 쿼리 변경
		List<Sort.Order> sorts = new ArrayList<>();
		
		sorts.add(Sort.Order.desc("createDate"));
		//게시물을 최신순으로 조회하기 위해 pageRequest.of 메서드의 세 번째 매개변수에 Sort 객체 전달
		//작성 일시를 역순으로 조회하려면 Sort.Order.desc("createDate") 작성
		//작성 일시 외 정렬 조건 추가하려면 sort.add 메서드 활용해 sorts 리스트에 추가
		//desc: 내림차순. asc: 오름차순
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Question> spec = search(kw);
		return this.questionRepository.findAllByKeyword(kw, pageable);		
	}
	
	//상세 조회
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		
		if(question.isPresent()) {
			//isPresent로 id 값이 존재하는지 검사
			return question.get();
		}else {
			//없는 경우 예외 클래스 실행
			throw new DataNotFoundException("question not found");
		}
	}
	//질문 생성
	public void create(String subject, String content, SiteUser user) {
		//제목과 내용을 매개변수로 받음
		Question q = new Question();
		
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		
		//리포지토리에 저장
		this.questionRepository.save(q);
	}
	//수정
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	//질문 삭제
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	//로그인한 사용자를 질문 엔티티에 추천인으로 저장
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
	
}
