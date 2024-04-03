package dac.bantads.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Withdrawal extends FinancialMovement {
    @NotNull
    private Long accountId;
}
