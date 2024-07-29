package com.bantads.model.dtos;

import com.bantads.model.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ManagerPerformanceDto {
    private Manager manager;
    private int totalClients;
    private double totalPositiveBalance;
    private double totalNegativeBalance;
}
