package com.moon.website.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest //H2 DB 자동 실행
public class PostRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After //테스트 끝날때마다 수행될 메소드. 남아있는 h2데이터 지우기 위함
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void read_Posts(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder() //posts테이블에 insert/update쿼리 실행. id값 있으면 ipdate, 없으면 insert
            .title(title)
            .content(content)
            .author("moon@naver.com")
            .build());

        //when
        List<Posts> postsList = postsRepository.findAll(); //posts에 있는 모든 데이터 조회

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_enroll() {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 01, 13, 0, 0, 0);
        postsRepository.save(Posts.builder()
                        .title("title")
                        .content("content")
                        .author("author")
                        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //thenA
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>> createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
