package dac.bantads.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "account")
public class Account implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long accountNumber;
    @Column
    private double balance;
    @Column(nullable = false)
    private double limit;
    @Column(nullable = false)
    private String clientCpf;
    @Column
    private String managerCpf;
    @Embedded
    private AccountApproval accountApproval;
}