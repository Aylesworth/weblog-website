package io.github.aylesw.weblog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    private String id;

    private String title;

    private String content;

    private String author;

    @DBRef
    private User authorDetails;

    private Date created;

    private Date updated;

    @DBRef
    private List<Comment> comments;

    public List<Comment> getComments() {
        if (comments == null) comments = new ArrayList<>();
        return comments;
    }
}
