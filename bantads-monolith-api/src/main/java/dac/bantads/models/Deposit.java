package dac.bantads.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Deposit extends FinancialMovement {
    @NotNull
    private Long accountId;
}
