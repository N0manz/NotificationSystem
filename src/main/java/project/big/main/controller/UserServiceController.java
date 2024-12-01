package project.big.main.controller;

import project.big.main.dto.UserServiceDto;
import project.big.main.entity.UserService;
import project.big.main.service.UserServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/services")
@RequiredArgsConstructor
public class UserServiceController {
    private final UserServiceService userServiceService;

    @PostMapping
    public ResponseEntity<UserService> addService(@PathVariable Long userId, @RequestBody UserServiceDto dto) {
        dto.setUserId(userId);
        UserService userService = userServiceService.addService(dto);
        return ResponseEntity.ok(userService);
    }
}
