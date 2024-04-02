package dac.bantads.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ManagerAccountApprovalDto {
    @NotNull
    private boolean isApproved;
    @NotBlank
    private Long accountId;
    @NotBlank
    private String managerCpf;
    @Column
    private String refusalReason;
}
