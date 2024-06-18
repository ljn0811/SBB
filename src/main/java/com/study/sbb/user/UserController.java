package com.study.sbb.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		// /user/signup URL이 GET으로 요청되면 회원 가입을 위한 템플릿 렌더링
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		// /user/signup이 POST로 요청되면 회원 가입 진행
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			//password1과 password2가 일치하는지 검증
			//bindingResult.rejectValue(필드명, 오류 코드, 오류 메시지)
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");	//비밀번호 불일치 시 오류 발생 코드
			return "signup_form";
		}
		
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		}catch (DataIntegrityViolationException e) {
			// TODO: 사용자 ID or Email 주소가 이미 존재하는 경우 발생하는 예외
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		}catch(Exception e) {
			e.printStackTrace();
			//bindingResult.reject(오류 코드, 오류 메시지); 검증에 의한 오류 외 일반적인 오류 발생 시 사용
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}	
		
		return "redirect:/";
	}	
	
	  @GetMapping("/login") 
	  public String login() { 
		  //로그인 폼 템플릿 출력 return
		  return "login_form"; 
	  }
	 
}
