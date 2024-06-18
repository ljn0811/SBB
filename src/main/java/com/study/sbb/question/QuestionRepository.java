package com.study.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	//JpaRepository JPA가 제공하는 인터페이스 중 하나. CRUD 작업 처리하는 메서드들을 이미 내장하고 잇어 데이터 관리 작업을 좀 더 편리하게 처리 가능.
	//JpaRepository<Question, Integer> Question 엔티티로 리포지터리를 생성한다는 의미. 기본키가 Integer임을 추가로 지정.
	//CRUD: Create, Read, Update, Delete 데이터 처리의 기본 기능.
	
	Question findBySubject(String subject); //subject 조회
	
	Question findBySubjectAndContent(String subject, String content); //content, subject 함께 조회. AND 연산자 사용
	
	List<Question> findBySubjectLike(String subject); //subject 열 값들 중 특정 문자열을 포함하는 데이터 조회
	
	Page<Question> findAll(Pageable pageable); //Paging 처리를 위해 Pageable 객체를 입력받아 Page<Question> 타입 객체 리턴.
	
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	//@Query는 엔티티 기준 작성
	@Query(	"select "
            	+ 	"distinct q "
            	+ 	"from Question q " 
            	+ 	"left outer join SiteUser u1 on q.author=u1 "
            	+ 	"left outer join Answer a on a.question=q "
            	+ 	"left outer join SiteUser u2 on a.author=u2 "
            	+ 	"where "
            	+ 	"   q.subject like %:kw% "
            	+ 	"   or q.content like %:kw% "
            	+ 	"   or u1.username like %:kw% "
            	+ 	"   or a.content like %:kw% "
            	+ 	"   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
