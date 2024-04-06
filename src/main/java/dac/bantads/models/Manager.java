package dac.bantads.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "manager")
public class Manager implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 14)
    private String cpf;
    @Column(nullable = false, length = 50)
    private String name;
    @ElementCollection
    private List<Long> accountIds;
}