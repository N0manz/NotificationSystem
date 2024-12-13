package project.big.main.controller;

import project.big.main.dto.AddServiceRequestDto;
import project.big.main.dto.UserServiceDto;
import project.big.main.service.NotificationService;
import project.big.main.service.UserLinkedServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users/{userId}/linked-services")
@RequiredArgsConstructor
public class UserLinkedServiceController {

    private final UserLinkedServiceService linkedServiceService;
    private final NotificationService notificationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    // Добавление нового сервиса
    @PostMapping
    public ResponseEntity<UserServiceDto> addService(@PathVariable Long userId,
                                                     @RequestBody AddServiceRequestDto dto) {
        logger.info("хуй хуй хуй хуй хуй хуй" + userId);
        logger.info("хуй хуй хуй" + dto);
        UserServiceDto responseDto = linkedServiceService.addService(userId, dto);
        return ResponseEntity.ok(responseDto);
    }

    // Получение списка сервисов
    @GetMapping
    public ResponseEntity<List<UserServiceDto>> getUserServices(@PathVariable Long userId) {
        List<UserServiceDto> services = linkedServiceService.getUserServices(userId);
        return ResponseEntity.ok(services);
    }

    // Удаление сервиса
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId) {
        linkedServiceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Map<String, Object>>> getUserNotifications(@PathVariable Long userId) {
        // Получаем список сервисов пользователя
        List<UserServiceDto> userServices = linkedServiceService.getUserServices(userId);

        // Передаём сервисы в Python-сервис для получения уведомлений
        List<Map<String, Object>> notifications = notificationService.fetchNotificationsFromPython(userServices);

        return ResponseEntity.ok(notifications);
    }




}
