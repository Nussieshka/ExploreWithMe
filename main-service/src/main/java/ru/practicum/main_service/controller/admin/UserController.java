package ru.practicum.main_service.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.model.dto.UserDTO;

import java.util.List;

@RestController("adminUserController")
@RequestMapping("/admin/users")
public class UserController {
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(List<Integer> ids,
                                                  @RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return null;
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO user) {
        return null;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        return null;
    }
}
