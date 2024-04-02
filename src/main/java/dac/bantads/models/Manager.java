package dac.bantads.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "manager")
public class Manager implements Serializable {
    @Id
    private String cpf;
    @Column(nullable = false, length = 50)
    private String name;
    @ElementCollection
    private List<String> clientCpfs;
}