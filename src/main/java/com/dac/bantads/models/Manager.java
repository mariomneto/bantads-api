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
@Table(name = "manager")
public class Manager implements Serializable {
    @Id
    private String cpf;

    @Column(nullable = false, length = 50)
    private String name;
}