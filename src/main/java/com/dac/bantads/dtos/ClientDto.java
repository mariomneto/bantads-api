package com.mp.bantads.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRegisterDto {
    @NotBlank
    @Size(max = 100)
    private String name;
    @Size(max = 50)
    private double monthlySalary;
    @NotBlank
    @Size(max = 30)
    private String password;
}