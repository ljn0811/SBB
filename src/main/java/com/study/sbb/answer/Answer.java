package com.study.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.study.sbb.question.Question;
import com.study.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	@ManyToOne 
	//질문 엔티티와 연결
	//N:1 관계. 답변 : 질문
	//실제 DB에서는 외래키 관계 형성
	// 부모 자식 관계를 갖는 구조에서 사용
	//외래키: 테이블과 테이블 사이 관계 구성 시 연결되는 열
	private Question question; //질문 엔티티 참조 위해 question 속성 추가
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	@ManyToMany
	Set<SiteUser> voter;
}
