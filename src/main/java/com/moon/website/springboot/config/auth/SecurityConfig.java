package com.moon.website.springboot.config.auth;

import com.moon.website.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable() //h2-console화면을 사용하기 위해 이 기능 disable()
                .and().authorizeRequests()//authorizeRequests()는 url별 권한 관리 설정.andMatchers 사용 가능
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() // 해당 url들은 전체 열람 권한을 줌
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())// api/v1/**주소를 가진 api는 user권한을 가진 사람만 사용 가능
                    .anyRequest().authenticated() //나머지 url들에 대해선 인증된 사용자(로그인한 사용자)에게만 허용
                .and().logout().logoutSuccessUrl("/") //로그아웃 성공시 이동 주소
                .and().oauth2Login() //oauth2Login은 oauth2로그인에 대한 설정의 진입점.
                    .userInfoEndpoint()//userInfoEndpoint은 oauth로그인 성공 이후 사용자 정보 가져올때 설정 담당.
                    .userService(customOAuth2UserService);  // userService는 소셜 로그인 성공시 후속 조치를 진행할 userService 인터페이스의 구현체 등록. 소셜 서비스에서 사용자 정보 가져온 후 추가 진행하려고자 하는 기능 명시 가능.
    }
}
