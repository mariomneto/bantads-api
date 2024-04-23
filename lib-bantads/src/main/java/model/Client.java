package dac.bantads.models;

import dac.bantads.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "client")
public class Client implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 14)
    private String cpf;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private double salary;
    @NotBlank
    private String email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}