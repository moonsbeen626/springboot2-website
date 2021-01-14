package com.moon.website.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") //SpringDataJap에서 지원하지 않는 메소드를 직접 작성해서 사용.
    List<Posts> findAllDesc();
}
