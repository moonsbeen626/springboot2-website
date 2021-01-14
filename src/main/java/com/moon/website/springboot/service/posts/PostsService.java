package com.moon.website.springboot.service.posts;

import com.moon.website.springboot.domain.posts.Posts;
import com.moon.website.springboot.domain.posts.PostsRepository;
import com.moon.website.springboot.web.dto.PostsListResponseDto;
import com.moon.website.springboot.web.dto.PostsResponseDto;
import com.moon.website.springboot.web.dto.PostsSaveRequestDto;
import com.moon.website.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream() // findAllDesc로 찾은 posts의 list들을 stream으로 사용 가능. 스트림은 람다식 기능이 있음
                .map(posts -> new PostsListResponseDto(posts)) //.map()함수는 스트림의 메소드. 요소들을 특정 조건에 해당하는 값으로 변경 해줌.
                // 여기에선 postsRepository 결과로 넘어온 Posts의 스트림을 map을 통해 PostsListResponseDto로 변환하고 있음.
                .collect(Collectors.toList()); //스트림 메소드. 스트림 배열의 원소들 가공이 끝났다면 리턴해줄 결과를 생성해주는 메소드. 여기선 List로 반환
                //Collectorss는 스트림 데이터를 수집하는 메소드.
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts); //JpaRepository에서 delete메소드를 지원. 존재하는 posts인지 확인 후 삭제.
    }
}
