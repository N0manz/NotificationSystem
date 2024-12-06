package project.big.main.controller;



import project.big.main.dto.RegisterRequestDto;
import project.big.main.entity.User;
import project.big.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
        // Проверяем, существует ли уже пользователь с таким email
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body("User with this email already exists.");
        }

        // Создаём нового пользователя
        User newUser = new User();
        newUser.setEmail(registerRequestDto.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(registerRequestDto.getPassword()));
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }

}

