package ru.practicum.main_service.service;

import ru.practicum.main_service.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers(List<Long> ids, Integer from, Integer size);

    UserDTO addUser(UserDTO user);

    void deleteUser(Long userId);
}
