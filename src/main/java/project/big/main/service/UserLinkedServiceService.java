package project.big.main.service;

import project.big.main.dto.AddServiceRequestDto;
import project.big.main.dto.UserServiceDto;
import project.big.main.entity.User;
import project.big.main.entity.UserLinkedService;
import project.big.main.repository.UserRepository;
import project.big.main.repository.UserLinkedServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserLinkedServiceService {

    private final UserRepository userRepository;
    private final UserLinkedServiceRepository userLinkedServiceRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Добавление нового сервиса
    public UserServiceDto addService(Long userId, AddServiceRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserLinkedService linkedService = new UserLinkedService();
        linkedService.setUser(user);
        linkedService.setServiceEmail(dto.getServiceEmail());
        linkedService.setLink(dto.getLink());
        linkedService.setServicePasswordHash(passwordEncoder.encode(dto.getServicePassword()));

        UserLinkedService savedService = userLinkedServiceRepository.save(linkedService);

        // Преобразование в DTO
        UserServiceDto responseDto = new UserServiceDto();
        responseDto.setId(savedService.getId());
        responseDto.setServiceEmail(savedService.getServiceEmail());
        responseDto.setLink(savedService.getLink());
        return responseDto;
    }

    // Получение всех сервисов пользователя
    public List<UserServiceDto> getUserServices(Long userId) {
        List<UserLinkedService> services = userLinkedServiceRepository.findByUserId(userId);

        // Преобразование сущностей в DTO
        return services.stream().map(service -> {
            UserServiceDto dto = new UserServiceDto();
            dto.setId(service.getId());
            dto.setServiceEmail(service.getServiceEmail());
            dto.setLink(service.getLink());
            return dto;
        }).collect(Collectors.toList());
    }

    // Удаление сервиса
    public void deleteService(Long serviceId) {
        try {
            if (!userLinkedServiceRepository.existsById(serviceId)) {
                throw new IllegalArgumentException("Service with ID " + serviceId + " does not exist");
            }
            userLinkedServiceRepository.deleteById(serviceId);
        } catch (Exception e) {
            System.err.println("Error deleting service: " + e.getMessage());
            throw e; // Пробрасываем исключение дальше
        }
    }

}
