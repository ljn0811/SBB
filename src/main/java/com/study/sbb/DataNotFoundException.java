package com.study.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//데이터베이스에서 특정 엔티티 또는 데이터를 찾을 수 없을 때 발생시키는 예외 클래스 생성
//예외 발생 시 설정된 HTTP 상태 코드 HttpStatus.NOT_FOUND와 이유(reason = "entity not found")를 포함한 응답을 생성하여 클라이언트에 반환.
//404에러 반환할 수 있게 작성
//RuntimeException 상속: 사용자 정의 예외 클래스 정의 방법 중 하나. 실행 시 발생하는 예외라는 의미
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {

	
	private static final long serialversionUID = 1L;
	
	public DataNotFoundException(String message) {
		super(message);
	}
}
