package com.moon.website.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); //로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 확인하기위함. optional로 널 값 체크하기 용이하도록.
}
