package project.big.main.dto;

import lombok.Data;

@Data
public class NotificationServiceRequestDto {
    private String username; // Email пользователя для авторизации
    private String password; // Пароль для авторизации
    private String link;     // Ссылка на сервис
}
