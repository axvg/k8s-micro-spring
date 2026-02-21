package com.example.app.micro.userservice.presentation.mapper;

import com.example.app.micro.userservice.domain.model.User;
import com.example.app.micro.userservice.presentation.dto.CreateUserRequest;
import com.example.app.micro.userservice.presentation.dto.UpdateUserRequest;
import com.example.app.micro.userservice.presentation.dto.UserResponse;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Mapper entre DTOs de presentación y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    /**
     * Convierte CreateUserRequest a User de dominio
     */
    User toDomain(CreateUserRequest request);

    /**
     * Convierte UpdateUserRequest a User de dominio
     */
    User toDomain(UpdateUserRequest request);

    /**
     * Convierte User de dominio a UserResponse
     */
    UserResponse toResponse(User user);

    /**
     * Convierte lista de Users a lista de UserResponse
     */
    List<UserResponse> toResponseList(List<User> users);
}
