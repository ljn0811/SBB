package com.study.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.study.sbb.answer.Answer;
import com.study.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity 
//스프링 부트가 이 클래스를 Entity로 인식
public class Question {

	@Id 
	//ID 속성을 기본키로 지정 >> ID 속성의 고유 번호들은 엔티티에서 각 데이터들을 구분하는 유효한 값으로 중복되면 안되기 때문
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	//데이터 저장 시 해당 속성에 값을 일일이 입력하지 않아도 자동으로 1씩 증가하여 저장된다.  GenerationType.IDENTITY 해당 속성만 별도로 번호가 차례대로 늘어나도록 할 때 사용한다.
	//strategy 옵션 생략 시 순서가 일정한 고유 번호를 가질 수 없게 된다.
	private Integer id;
	
	@Column(length = 200)
	//열의 세부 설정
	//length: 열의 길이 설정 시 사용
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	//열 데이터 유형이나 성격 정의 시 사용
	//columnDefinition = "TEXT": 텍스트를 열 데이터로 넣을 수 있음을 의미. 글자 수 제한 불가한 경우 사용
	//엔티티의 속성은 @Column 어노테이션을 사용하지 않아도 테이블의 열로 인식. 
	//@Transient : 테이블의 열로 인식하고 싶지 않을 때 사용. 엔티티 속성을 테이블의 열로 만들지 않고 클래스 속성 기능으로만 사용하고자 할 때 사용.
	private String content;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	//1:N 관계. 질문과 답변
	//mappedBy: 참조 엔티티의 속성명.
	//CascadeType.REMOVE 질문 삭제 시 그에 달린 답변들도 모두 삭제 가능.
	private List<Answer> answerList;
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	@ManyToMany
	//다대다 관계
	//Set으로 작성한 이유: voter 속성값이 서로 중복되지 않도록 하기 위해서
	Set<SiteUser> voter;
}
