package io.github.aylesw.weblog.repository;

import io.github.aylesw.weblog.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAllByOrderByCreatedDesc();

    Post findByCommentsId(String commentId);

    @Query(
            value = "{ $or: [ { 'title': { $regex: /?0/, $options: 'i' } }, { 'content': { $regex: /?0/, $options: 'i' } } ] }",
            sort = "{ 'created' : -1 }"
    )
    List<Post> findByTitleOrContentContaining(String keyword);

    List<Post> findByAuthorOrderByCreatedDesc(String author);
}
