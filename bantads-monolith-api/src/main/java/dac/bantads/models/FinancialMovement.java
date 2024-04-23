package dac.bantads.models;

import dac.bantads.enums.FinancialMovementType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "financialMovement")
public class FinancialMovement implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "DATE")
    private Date date;
    @Column(nullable = false)
    private FinancialMovementType type;
    @Column(nullable = false)
    private double value;

}