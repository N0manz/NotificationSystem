package project.big.main.service;

import project.big.main.dto.UserServiceDto;
import project.big.main.entity.User;
import project.big.main.entity.UserService;
import project.big.main.repository.UserRepository;
import project.big.main.repository.UserServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceService {
    private final UserServiceRepository userServiceRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService addService(UserServiceDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserService userService = new UserService();
        userService.setUser(user);
        userService.setServiceEmail(dto.getServiceEmail());
        userService.setServicePasswordHash(passwordEncoder.encode(dto.getServicePassword()));
        return userServiceRepository.save(userService);
    }
}
