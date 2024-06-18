package com.study.sbb.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true) //unique = true: 유일한 값만 저장 가능. 값을 중복되게 저장할 수 없음. DB에 유니크 인덱스로 생성
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;
}
