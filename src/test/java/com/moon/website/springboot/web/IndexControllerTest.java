package com.moon.website.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate; //<-> MockMvc, TestRestTemplate은 서블릿 컨테이너를 사용해 실제 서버가 동작하는 것 처럼 테스트 수행 가능.

    @Test
    public void 메인페이지_로딩() {
        //when
        String body = this.testRestTemplate.getForObject("/", String.class); //주어진 URL 주소로 HTTP GET 메서드로 객체로 결과를 반환받는다

        //then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }
}
