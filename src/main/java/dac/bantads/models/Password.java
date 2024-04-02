package dac.bantads.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@Table(name = "password")
public class Password {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false, length = 14)
    private String userId;
    @Column(nullable = false, length = 60)
    private String password;
}
