package com.study.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

	//question_form.html 템플릿 입력 항목인 subject와 content가 폼 클래스의 subject, content 속성과 바인딩됨.
	//바인딩: 템플릿 항목과 form 클래스 속성이 매핑되는 과정
	
	@NotEmpty(message = "제목은 필수 항목입니다.") //해당 값이 Null 또는 빈 문자열("")을 허용하지 않음. message: 검증 실패 시 화면에 표시할 오류 메시지
	@Size(max=200) //최대 길이가 200byte를 넘으면 안 된다는 의미.
	private String subject;
	
	@NotEmpty(message = "내용은 필수 항목입니다.")
	private String content;
}
