package io.github.aylesw.weblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private String id;
    private String email;
    private UserDto user;
    private String content;
    private Date posted;
    private Integer likes;
    private Boolean liked;
    private Integer totalReplies;
}
