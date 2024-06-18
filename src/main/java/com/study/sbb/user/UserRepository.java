package com.study.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	//Siteuser의 기본 키 타입은 Long >> JpaRepository<SiteUser, Long>
	Optional<SiteUser> findByusername(String username);
}
