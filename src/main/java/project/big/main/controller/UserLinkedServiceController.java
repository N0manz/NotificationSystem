package project.big.main.controller;

import project.big.main.dto.AddServiceRequestDto;
import project.big.main.dto.UserServiceDto;
import project.big.main.service.UserLinkedServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/linked-services")
@RequiredArgsConstructor
public class UserLinkedServiceController {

    private final UserLinkedServiceService linkedServiceService;

    // Добавление нового сервиса
    @PostMapping
    public ResponseEntity<UserServiceDto> addService(@PathVariable Long userId,
                                                     @RequestBody AddServiceRequestDto dto) {
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
}
