package io.github.aylesw.weblog.service.impl;

import io.github.aylesw.weblog.dto.UserDto;
import io.github.aylesw.weblog.entity.User;
import io.github.aylesw.weblog.exception.ResourceNotFoundException;
import io.github.aylesw.weblog.repository.UserRepository;
import io.github.aylesw.weblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user = userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return mapper.map(user, UserDto.class);
    }
}
