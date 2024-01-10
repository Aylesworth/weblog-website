package io.github.aylesw.weblog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    private String id;

    private String email;

    private String content;

    private Date posted;

    private Integer likes;

    @DBRef
    private List<Comment> replies;

    public List<Comment> getReplies() {
        if (replies == null) replies = new ArrayList<>();
        return replies;
    }
}
