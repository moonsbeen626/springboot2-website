package com.moon.website.springboot.web;

import com.moon.website.springboot.service.posts.PostsService;
import com.moon.website.springboot.web.dto.PostsResponseDto;
import com.moon.website.springboot.web.dto.PostsSaveRequestDto;
import com.moon.website.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // 생성자로 bean객체를 받도록. 즉 autowired와 같은 효과. final이 선언된 모든 필드를 인자값으로 하는 생성자 자동 생성.
@RestController
public class PostsApiController {

    private final PostsService postsService; //postsService가 등록/

    @PostMapping("/api/v1/posts") //등록
    public Long save(@RequestBody PostsSaveRequestDto requestDto) { //PostsSaveRequestDto를 자바 객체 형태로 전달받음
        return postsService.save(requestDto); //전달 받은 객체를 저장.
    }

    @PutMapping("/api/v1/posts/{id}") // 수정
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) { //PathVariabled은 동일한 이름을 가진 파라미터에 매핑
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}") //조회
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}") //삭제
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
