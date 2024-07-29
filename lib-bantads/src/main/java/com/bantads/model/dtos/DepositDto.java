package com.bantads.model.dtos;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class DepositDto {
    @NotNull
    private Long accountId;
    @NotNull
    private double value;
    @Column(nullable = false, columnDefinition = "DATE")
    private Date date;
}
