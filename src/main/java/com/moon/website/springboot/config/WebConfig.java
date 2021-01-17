package com.moon.website.springboot.config;

import com.moon.website.springboot.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration // 직접 제어가 불가능한 외부 라이브러리 또는 설정을 ioc컨테이너에 bean으로 등록하기 위함
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    //HandlerMethodArgumentResolver는 항상 아래 메소드를 통해 추가해야 스프링에서 인식 가능.
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }
}
