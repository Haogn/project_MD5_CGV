package com.ra.mapper;

import com.ra.dto.response.UserResponse;
import com.ra.entity.Users;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserResponse toUserResponse(Users users) {
        return UserResponse.builder()
                .id(users.getId())
                .email(users.getEmail())
                .userName(users.getUserName())
                .status(users.getStatus())
                .birthday(users.getBirthday())
                .roles(users.getRoles().stream().map(item -> item.getRoleName().name()).collect(Collectors.toList()))
                .memberLever(users.getMemberLevers().name())
                .scorePoints(users.getScorePoints())
                .build();
    }
}
