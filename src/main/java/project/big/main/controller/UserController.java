package project.big.main.controller;

import project.big.main.dto.UserRegistrationDto;
import project.big.main.entity.User;
import project.big.main.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto dto) {
        User user = userService.registerUser(dto);
        return ResponseEntity.ok(user);
    }
}
