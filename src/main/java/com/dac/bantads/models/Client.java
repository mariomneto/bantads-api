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
@Table(name = "client")
public class User implements Serializable {
    @Id
    private String cpf;

    @Column(nullable = false, length = 14)
    private String name;
    @Column(nullable = false, length = 14)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
        return List.of(new SimpleGrantedAuthority("CLIENT")); //retornar role certo
    }
}