package ru.practicum.main_service.model.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.main_service.model.dto.UserDTO;
import ru.practicum.main_service.model.entity.UserEntity;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(UserEntity user);

    UserEntity toUserEntity(UserDTO userDTO);

    @IterableMapping(elementTargetType = UserDTO.class)
    List<UserDTO> toUserDTO(Iterable<UserEntity> users);
}
