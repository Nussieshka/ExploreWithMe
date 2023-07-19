package ru.practicum.main_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.model.dto.UserDTO;
import ru.practicum.main_service.model.mapper.UserMapper;
import ru.practicum.main_service.service.UserService;
import ru.practicum.main_service.repository.UserRepository;
import ru.practicum.main_service.util.MessageResponseStatusException;
import ru.practicum.main_service.util.Util;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getUsers(List<Long> ids, Integer from, Integer size) {
        if (ids == null || ids.isEmpty()) {
            return UserMapper.INSTANCE.toUserDTO(userRepository.findAll(PageRequest.of(from / size, size)));
        }
        return UserMapper.INSTANCE.toUserDTO(userRepository.getAllByIdIn(ids, PageRequest.of(from / size, size)));
    }

    @Transactional
    @Override
    public UserDTO addUser(UserDTO user) {
        Long userId = user.getId();
        if (userId != null) {
            throw MessageResponseStatusException.getNonNullIdException(userId);
        }
        Util.validateUserDTOEmail(user);
        return UserMapper.INSTANCE.toUserDTO(userRepository.save(UserMapper.INSTANCE.toUserEntity(user)));
    }

    @Override
    public void deleteUser(Long userId) {
        if (userId == null) {
            throw MessageResponseStatusException.getNullIdException();
        }

        userRepository.deleteById(userId);
    }
}
