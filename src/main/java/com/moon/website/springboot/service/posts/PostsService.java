package com.moon.website.springboot.service.posts;

import com.moon.website.springboot.domain.posts.Posts;
import com.moon.website.springboot.domain.posts.PostsRepository;
import com.moon.website.springboot.web.dto.PostsResponseDto;
import com.moon.website.springboot.web.dto.PostsSaveRequestDto;
import com.moon.website.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional //트랜젝션 처리
    public Long save(PostsSaveRequestDto requestDto) { //등록
        return postsRepository.save(requestDto.toEntity()).getId(); // 전달받은 requestDto를 entity형태로 변환한 후 id값을 가져와서 저장.
                                                                    // 이때 save함수는 들어오는 객체가 새로운 객체인지 아닌지 판별해서 insert/update진행
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) { //수정
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id) { //조회
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }
}
