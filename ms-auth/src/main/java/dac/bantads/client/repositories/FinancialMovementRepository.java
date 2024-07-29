package dac.bantads.client.repositories;

import com.bantads.model.FinancialMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialMovementRepository extends JpaRepository<FinancialMovement, Long> {
    boolean existsById(Long id);
    Optional<FinancialMovement> findById(Long id);
    List<FinancialMovement> findAllWhereClientId(Long id);
    void deleteById(Long id);
    void update(FinancialMovement financialMovement);
}