package project.big.main.dto;

import lombok.Data;

@Data
public class UserServiceDto {
    private Long id; // ID записи сервиса
    private String serviceEmail;
    private String Link;
}

