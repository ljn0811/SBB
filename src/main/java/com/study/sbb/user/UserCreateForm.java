package com.study.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	
	//입력받는 데이터 길이가 3~25 사이여야 한다는 검증 조건 설정
	//Size(min=?, max=?): 문자열의 길이가 최소 길이와 최대 길이 사이 해당하는지 검증
	@Size(min = 3, max = 25) 	
	@NotEmpty(message = "사용자 ID는 필수 항목입니다.")
	private String username;
	
	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	private String password1;
	
	@NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
	private String password2;
	
	@NotEmpty(message = "이메일은 필수 항목입니다.")
	@Email //해당 속성 값이 이메일 형식과 일치하는지 검증
	private String email;
	
}
