package com.bantads.model;

import com.bantads.enums.AccountApprovalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Embeddable
public class AccountApproval {
    @Column(nullable = false)
    private AccountApprovalStatus accountApprovalStatus;
    @Column(columnDefinition = "DATE")
    private Date evaluationDate;
    @Column
    private String refusalReason;
}
