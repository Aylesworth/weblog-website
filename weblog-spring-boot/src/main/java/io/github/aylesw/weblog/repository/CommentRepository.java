package io.github.aylesw.weblog.repository;

import io.github.aylesw.weblog.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    Comment findByRepliesId(String commentId);
}
