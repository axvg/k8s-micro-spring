package com.example.app.micro.productservice.infrastructure.client;

import com.example.app.micro.productservice.domain.model.User;
import com.example.app.micro.productservice.infrastructure.client.dto.UserDto;
import com.example.app.micro.productservice.infrastructure.client.mapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserClient {

    private final RestTemplate restTemplate;
    private final UserDtoMapper userDTOMapper;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public User getUserById(Long userId) {
        log.info(
            "Calling User Service (PostgreSQL userdb) to get user with id: {}",
            userId
        );

        String url = this.userServiceUrl + "/api/users/" + userId;

        try {
            UserDto user = restTemplate.getForObject(url, UserDto.class);
            log.info("User retrieved successfully from userdb: {}", user);
            return userDTOMapper.toDomain(user);
        } catch (Exception e) {
            log.error("Error calling User Service: {}", e.getMessage());
            throw new RuntimeException(
                "Error calling User Service: " + e.getMessage()
            );
        }
    }
}
