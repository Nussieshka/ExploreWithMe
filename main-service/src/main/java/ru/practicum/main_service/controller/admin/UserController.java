package ru.practicum.main_service.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.UserDTO;
import ru.practicum.main_service.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController("adminUserController")
@RequestMapping("/admin/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false) List<Long> ids,
                                                  @RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(service.getUsers(ids, from, size));
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody @Valid UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addUser(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        service.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
