package com.moon.website.springboot.web;

import com.moon.website.springboot.domain.posts.Posts;
import com.moon.website.springboot.domain.posts.PostsRepository;
import com.moon.website.springboot.web.dto.PostsSaveRequestDto;
import com.moon.website.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //랜덤 포트로 실행
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                            .title(title)
                                            .content(content)
                                            .author("moon@naver.com")
                                            .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when. ResponseEntury는 응답을 받을 entity. 응답 종류가 메시지, 오류 등 다양하므로 다양한 응답을 받을 수 있는 객체.
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class); //restTemplate은 Rest api호출 이후 응답을 받을때까지 기다리는 동기 방식.
                                                            //postForEntity(url, request object, class type)는 url로 post요청을 보내고 결과로 responseEntity를 받음.
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder() //테스트할 posts객체를 하나 만듦
                            .title("title")
                            .content("content")
                            .author("moon@naver.com")
                            .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                            .title(expectedTitle)
                            .content(expectedContent)
                            .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto); //requestDto를 HttpEntity타입으로 변경

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class); //업데이트니까 HttpMethod.put
                                                            //exchange(url, any HttpMethod, 원하는 데이터 형태, 원하는 클래스 타입.class)로 원하는 데이터 형식으로 정보 받아옴

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }
}
