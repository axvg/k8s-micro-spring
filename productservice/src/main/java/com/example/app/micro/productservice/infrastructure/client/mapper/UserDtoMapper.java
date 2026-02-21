package com.example.app.micro.productservice.infrastructure.client.mapper;

import com.example.app.micro.productservice.domain.model.User;
import com.example.app.micro.productservice.infrastructure.client.dto.UserDto;
import com.example.app.micro.productservice.presentation.dto.UserResponse;
import org.mapstruct.Mapper;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    User toDomain(UserDto dto);

    UserResponse toResponse(User user);
}
