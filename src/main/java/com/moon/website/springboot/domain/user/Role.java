package com.moon.website.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_QUEST", "손님"), //스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야함.
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
