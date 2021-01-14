package com.moon.website.springboot.web;

import com.moon.website.springboot.service.posts.PostsService;
import com.moon.website.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor //final필드 의존성 주입 어노테이션 없이
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) { //Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장.
        model.addAttribute("posts", postsService.findAllDesc()); //postsService.findAllDesc로 가져온 결과를 posts로 index.mustache에 전달
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
