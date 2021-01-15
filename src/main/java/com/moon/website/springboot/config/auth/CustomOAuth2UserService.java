package com.moon.website.springboot.config.auth;

import com.moon.website.springboot.config.auth.dto.OAuthAttributes;
import com.moon.website.springboot.config.auth.dto.SessionUser;
import com.moon.website.springboot.domain.user.User;
import com.moon.website.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registerationId = userRequest.getClientRegistration().getRegistrationId(); //현재 로그인중인 서비스 구분. 구글인지, 네이버인지 등
        //로그인 진행시 키가 되는 필드 값. primary key와 같은 의미. 구글은 기본 코드 "sub"로 지원. 네이버, 카카오등은 기본 지원하지 않음
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes authAttributes = OAuthAttributes.of(registerationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(authAttributes);

        httpSession.setAttribute("user", new SessionUser(user)); //SessionUser는 세션에 사용자 정보를 저장하기 위한 dto

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                authAttributes.getAttributes(),
                authAttributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes oAuth2Attributes) {
        User user = userRepository.findByEmail(oAuth2Attributes.getEmail()).map(entity -> entity.update(oAuth2Attributes.getName(), oAuth2Attributes.getPicture()))
                .orElse(oAuth2Attributes.toEntity());

        return userRepository.save(user);
    }
}
