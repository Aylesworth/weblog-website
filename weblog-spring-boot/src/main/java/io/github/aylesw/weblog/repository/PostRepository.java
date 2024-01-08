package io.github.aylesw.weblog.repository;

import io.github.aylesw.weblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY created DESC")
    List<Post> findAllOrderByCreatedDesc();
}
