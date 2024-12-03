package project.big.main.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_service") // Таблица для хранения сервисов пользователя
@Data
public class UserLinkedService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор сервиса

    @ManyToOne(fetch = FetchType.LAZY) // Связь с пользователем
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = true)
    private String link;

    @Column(nullable = false)
    private String serviceEmail; // Email внешнего сервиса

    @Column(nullable = false)
    private String servicePasswordHash; // Хэш пароля внешнего сервиса
}
