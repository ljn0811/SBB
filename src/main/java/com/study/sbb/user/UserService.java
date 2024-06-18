package com.study.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String email, String password) {
		//userRepository를 사용해 User 데이터를 생성하는 create 메서드 추가
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		//비밀번호 암호화하여 저장
		//BCryptPasswordEncoder: 비크립트 해시 함수 사용
		//비크립트: 해시 함수의 하나. 비밀번호와 같은 보안 정보를 안전하게 저장하고 검증할 때 사용하는 암호화 기술
		//객체를 new로 생성하는 것보다는 빈으로 등록하여 사용하는 것이 더 좋음 >> 암호화 방식 변경 시 비크립트패스워드인코더 객체를 사용한 모든 프로그램을 일일이 찾아야 함.
		/* BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); */
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
	}

	
	  public SiteUser getUser(String username) { 
		  Optional<SiteUser> siteUser = this.userRepository.findByusername(username); 
		  if(siteUser.isPresent()) {
			  return siteUser.get(); 
		  }else { 
			  //사용자명에 해당하는 데이터가 없을 경우 throw new
			 throw new DataNotFoundException("siteuser not found"); 
		} 
	  }
	 
}
