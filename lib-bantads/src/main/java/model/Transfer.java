package dac.bantads.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Transfer extends FinancialMovement {
    @NotNull
    private Long originAccountId;
    @NotNull
    private Long destinationAccountId;
}
