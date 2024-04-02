package dac.bantads.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientUpdateDto {
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private double salary;
    @NotBlank
    private String email;
}
