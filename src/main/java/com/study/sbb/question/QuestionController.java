package com.study.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.study.sbb.answer.AnswerForm;
import com.study.sbb.user.SiteUser;
import com.study.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor //생성자. 롬복 제공. final이 붙은 속성을 포함하는 생성자를 자동으로 만들어주는 역할.
@Controller
public class QuestionController {
	
	//객체 주입
	private final QuestionService questionService; 
	private final UserService userService;
	
	//질문 목록
	@GetMapping("/list")
	//@ResponseBody
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) { 
		//@RequestParam(value="page", defaultValue="0") int page
		//요청된 url에서 page 값을 가져오기 위해 list 메서드 매개변수 추가. page가 매개변수로 전달되지 않은 경우 기본 값 0
		//@RequestParam(value = "kw", defaultValue = "") String kw >> 검색어가 입력되지 않을 경우 kw값이 null이 되는 것을 방지하기 위해 빈 문자열을 기본값으로 설정
		
		Page<Question> paging = this.questionService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		//Model 객체: 자바 클래스와 템플릿 간 연결 고리 역할.		
		//질문 목록이 담긴 데이터 조회
		/* List<Question> questionList = this.questionService.getList(); */
		
		//템플릿에서 값 사용 가능
		/* model.addAttribute("questionList", questionList); */
		
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		//@PathVariable: 변하는 id 값을 얻을 때 사용 {} 안의 이름과 @PathVariable 매개변수 이름이 동일해야함
		
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		
		return "question_detail";
	}
	
	//질문 상세보기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		//매개 변수가 다르면 메서드명이 동일해도 사용 가능. >> 오버로딩
		//QuestionForm과 같이 매개변수로 바인딩한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용 가능
		return "question_form";
	}
	
	//질문 생성
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		//subject와 content를 questionForm 객체로 변경.
		//@Valid: @NotEmpty, @Size 등으로 설정한 검증 기능 동작
		//BindingResult: @Valid 뒤에 위치해야 함. 위치 부정확 >> @Valid만 적용 >> 입력 값 검증 실패 시 400 에러 발생
		if(bindingResult.hasErrors()) {
			//오류가 있는 경우 다시 질문 작성 화면으로 돌아갈 수 있게 함.
			return "question_form";
		}
		//principal: 로그인을 해야만 생성되는 객체. 로그아웃 상태면 principal 객체에 값이 없어 오류 발생
		//@PreAuthorize("isAuthenticated()") >> 로그인한 경우에만 실행. 해당 메서드는 로그인한 사용자만 호출 가능
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if (!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	
	//질문 수정
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			//로그인한 사용자와 질문의 작성자가 동일하지 않은 경우
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
				
		return String.format("redirect:/question/detail/%s", id);
	}
	
	//질문 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		
		this.questionService.delete(question);
		return "redirect:/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
}
