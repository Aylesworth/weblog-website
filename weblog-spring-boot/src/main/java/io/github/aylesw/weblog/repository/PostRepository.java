package io.github.aylesw.weblog.repository;

import io.github.aylesw.weblog.entity.Comment;
import io.github.aylesw.weblog.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAllByOrderByCreatedDesc();
}
