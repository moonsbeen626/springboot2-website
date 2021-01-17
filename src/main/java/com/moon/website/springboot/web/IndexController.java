package com.moon.website.springboot.web;

import com.moon.website.springboot.config.auth.LoginUser;
import com.moon.website.springboot.config.auth.dto.SessionUser;
import com.moon.website.springboot.service.posts.PostsService;
import com.moon.website.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor //final필드 의존성 주입 어노테이션 없이
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    //어느 컨트롤러든지 @LoginUser을 사용하면 세션 정보를 가져올 수 있음.
    public String index(Model model, @LoginUser SessionUser user) { //Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장.
        model.addAttribute("posts", postsService.findAllDesc()); //postsService.findAllDesc로 가져온 결과를 posts로 index.mustache에 전달

        if(user != null) {
            model.addAttribute("userName", user.getName());
        }

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
