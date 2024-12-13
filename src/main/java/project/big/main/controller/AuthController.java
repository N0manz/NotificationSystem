package project.big.main.controller;

import project.big.main.dto.RegisterRequestDto;
import project.big.main.util.JwtUtil;
import project.big.main.dto.LoginRequestDto;
import project.big.main.entity.User;
import project.big.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Название файла register.html (без расширения)
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body("User with this email already exists.");
        }

        User newUser = new User();
        newUser.setEmail(registerRequestDto.getEmail());
        newUser.setPasswordHash(registerRequestDto.getPassword()); // Сохраняем пароль как есть
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Название файла register.html (без расширения)
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequest) {
        logger.info("Entering login method with email: {}", loginRequest.getEmail());

        // Находим пользователя по email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    logger.warn("User not found with email: {}", loginRequest.getEmail());
                    return new RuntimeException("User not found");
                });

        // Проверяем пароли (сравниваем напрямую)
        if (!loginRequest.getPassword().equals(user.getPasswordHash())) {
            logger.warn("Invalid password for user: {}", loginRequest.getEmail());
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        // Генерируем токен
        String token = JwtUtil.generateToken(user.getId().toString());
        logger.info("Generated JWT Token for user: {}", loginRequest.getEmail());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user_id", user.getId().toString()
        ));
    }

}
