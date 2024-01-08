package io.github.aylesw.weblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String email;
    private String content;
    private LocalDateTime posted;
    private Integer likes;
    private List<CommentDto> replies;
}
