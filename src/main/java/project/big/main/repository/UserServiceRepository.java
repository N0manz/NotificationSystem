package project.big.main.repository;

import project.big.main.entity.UserService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserServiceRepository extends JpaRepository<UserService, Long> {
}
