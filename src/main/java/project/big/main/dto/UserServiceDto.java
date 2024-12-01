package project.big.main.dto;

import lombok.Data;

@Data
public class UserServiceDto {
    private Long userId;
    private String serviceEmail;
    private String servicePassword;
}

