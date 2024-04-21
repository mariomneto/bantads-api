package dac.bantads.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ManagerUpdateDto {
    @NotBlank
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(max = 14)
    private String password;
    @NotBlank
    @Size(max = 100)
    private String name;
    @Column(nullable = false)
    private double monthlySalary;
}
