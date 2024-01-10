package io.github.aylesw.weblog.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotEmpty
    private String id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String pictureUrl;
}
