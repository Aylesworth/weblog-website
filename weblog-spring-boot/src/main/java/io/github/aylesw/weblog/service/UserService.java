package io.github.aylesw.weblog.service;

import io.github.aylesw.weblog.dto.UserDto;

public interface UserService {
    UserDto saveUser(UserDto userDto);

    UserDto getUserInfo(String email);
}
