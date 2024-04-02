package dac.bantads.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class Withdrawal extends FinancialMovement {
    @NotNull
    private Long accountId;
}
