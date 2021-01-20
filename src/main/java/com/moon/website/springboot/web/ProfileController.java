package com.moon.website.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment environment;

    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles()); //현재 실행중인 activeProfile을 모두 가져온다다
       List<String> realProfiles = Arrays.asList("real", "resl1", "real2");
        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
    }
}
