package project.big.main.repository;

import project.big.main.entity.UserLinkedService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLinkedServiceRepository extends JpaRepository<UserLinkedService, Long> {

    // Получение всех сервисов, принадлежащих конкретному пользователю
    List<UserLinkedService> findByUserId(Long userId);
}
