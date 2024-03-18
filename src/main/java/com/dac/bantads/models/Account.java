package com.mp.venusian.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "account")
public class Account implements Serializable {
    @Id
    private int accountNumber;

    @Column(nullable = false)
    private float accountLimit;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date registrationDate;
}