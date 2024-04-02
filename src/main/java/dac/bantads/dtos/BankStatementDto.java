package dac.bantads.dtos;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class BankStatementDto {
    @Column(nullable = false, columnDefinition = "DATE")
    private Date startDate;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date endDate;
}
