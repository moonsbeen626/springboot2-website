package com.moon.website.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //만든 어노테이션이 생성될 수 있는 위치 지정. 여기선 PARAMETER이니 파라미터로 선언된 객체에서만 사용 가능.
@Retention(RetentionPolicy.RUNTIME) //어느 시점까지 어노테이션의 메모리를 가져갈지 지정. RUNTIME시 JVM이 런타임환경을 구성하고 런타임 종료시까지 메모리가 살아있음.
public @interface LoginUser { //@interface로 이 class를 어노테이션 class로 지정. 여기선 @LoginUser라는 이름의 어노테이션 생성
}
