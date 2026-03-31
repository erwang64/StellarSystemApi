package edu.esiea.stellarsystemapi.security.controllers.dto.mappers;

import edu.esiea.stellarsystemapi.security.controllers.dto.UserRequest;
import edu.esiea.stellarsystemapi.security.controllers.dto.UserResponse;
import edu.esiea.stellarsystemapi.security.model.Profile;
import edu.esiea.stellarsystemapi.security.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User toEntity(UserRequest dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        if (dto.getProfile() != null && !dto.getProfile().isBlank()) {
            user.setProfile(Profile.valueOf(dto.getProfile().toUpperCase()));
        }
        return user;
    }

    public static UserResponse toDto(User entity) {
        if (entity == null) {
            return null;
        }
        return new UserResponse(entity);
    }

    public static List<UserResponse> toDtoList(List<User> entities) {
        if (entities == null) {
            return null;
        }
        List<UserResponse> dtos = new ArrayList<>();
        for (User user : entities) {
            dtos.add(toDto(user));
        }
        return dtos;
    }
}
