package project.big.main.dto;

import lombok.Data;

@Data
public class AddServiceRequestDto {
    private String Link;
    private String serviceEmail;
    private String servicePassword;
}
