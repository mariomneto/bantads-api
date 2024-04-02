package dac.bantads.repositories;

import dac.bantads.models.Client;
import dac.bantads.models.FinancialMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FinancialMovementRepository extends JpaRepository<FinancialMovement, Long> {
    boolean existsById(Long id);
    Optional<FinancialMovement> findById(Long id);
    List<FinancialMovement> findAllWhereClientId(Long id);
    void deleteById(Long id);
    void update(FinancialMovement financialMovement);
}