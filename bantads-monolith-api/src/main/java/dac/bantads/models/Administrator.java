package dac.bantads.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "administrator")
public class Administrator implements Serializable {
    @Id
    private String email;
    @ElementCollection
    private List<String> clientCpfs;
}