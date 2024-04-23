package dac.bantads.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginDto {
    @NotBlank
    @Size(max = 100)
    private String email;
    @NotBlank
    @Size(max = 50)
    private String password;
}