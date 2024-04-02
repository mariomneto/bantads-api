package dac.bantads.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "movementHistory")
public class MovementHistory implements Serializable {
    @Column(nullable = false, columnDefinition = "DATE")
    private Date movementDate;

    @Column(nullable = false)
    private float accountLimit;

}