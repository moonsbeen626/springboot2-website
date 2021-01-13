package com.moon.website.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // jpa entity들이 BaseTimeEntity를 상속할 경우 필드들(createdData...) 도 칼럼으로 인식하도록
@EntityListeners(AuditingEntityListener.class) // 생성일, 수정일 자동으로 찾아 설정 활성화
public class BaseTimeEntity {

    @CreatedDate //entity 생성되어 저장될때 시간이 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;
}
