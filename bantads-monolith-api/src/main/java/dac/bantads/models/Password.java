package dac.bantads.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@Table(name = "password")
public class Password {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 14)
    private Long userId;
    @Column(nullable = false, length = 60)
    private String password;
}
