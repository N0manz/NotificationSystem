package project.big.main.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.big.main.dto.UserServiceDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> fetchNotificationsFromPython(List<UserServiceDto> userServices) {
        String url = "http://localhost:5000/parse-notifications";

        // Создание заголовков
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Подготовка тела запроса
        List<Map<String, String>> requestPayload = userServices.stream()
                .map(service -> Map.of(
                        "username", service.getServiceEmail(),
                        "password", "Pafuda33",
                        "link", service.getLink() // Ссылка на сервис
                ))
                .collect(Collectors.toList());

        HttpEntity<List<Map<String, String>>> request = new HttpEntity<>(requestPayload, headers);

        // Отправка POST-запроса
        ResponseEntity<List> response = restTemplate.postForEntity(url, request, List.class);

        return response.getBody(); // Возвращаем уведомления
    }}