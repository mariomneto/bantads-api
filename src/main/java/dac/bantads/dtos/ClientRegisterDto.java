package dac.bantads.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRegisterDto {
    @NotBlank
    @Size(max = 14)
    private String cpf;
    @NotBlank
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(max = 14)
    private String password;
    @NotBlank
    @Size(max = 100)
    private String name;
    @Column
    private double monthlySalary;
}