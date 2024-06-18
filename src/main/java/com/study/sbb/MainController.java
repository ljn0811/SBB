package com.study.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@GetMapping("/sbb")
	@ResponseBody
	public String index() {

		return "안녕하세요 SBB에 오신 것을 환영합니다.";
	}
	
	@GetMapping("/")
	public String root() {
		//redirect:/ 클라이언트가 요청하면 새로운 URL로 전송하는 것
		return "redirect:/question/list";
	}
}
