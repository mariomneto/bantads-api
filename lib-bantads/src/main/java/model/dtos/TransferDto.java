package dac.bantads.dtos;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class TransferDto {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long originAccountId;
    @NotNull
    private Long destinationAccountId;
    @NotNull
    private double value;
    @Column(nullable = false, columnDefinition = "DATE")
    private Date date;
}
