package com.stockhelt.backend.user.mapper;

import com.stockhelt.backend.user.dto.UserCreationDTO;
import com.stockhelt.backend.user.dto.UserDTO;
import com.stockhelt.backend.user.dto.UserMinimalDTO;
import com.stockhelt.backend.user.model.Doctor;
import com.stockhelt.backend.user.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMinimalDTO toMinimalDto(User user);

    @Mappings(
            @Mapping(target = "role", ignore = true)
    )
    UserDTO toDto(User user);

    User fromDto(UserDTO user);

    User fromCreationDto(UserCreationDTO user);

    @AfterMapping
    default void populateRole(User user, @MappingTarget UserDTO userDTO) {
        userDTO.setRole(user.getClass().getSimpleName());
    }

    Doctor docFromUserCreationDto(UserCreationDTO user);

}
