package project.big.main.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String serviceEmail;

    @Column(nullable = false)
    private String servicePasswordHash;
}
