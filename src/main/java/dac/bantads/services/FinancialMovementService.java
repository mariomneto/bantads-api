package dac.bantads.services;

import dac.bantads.models.FinancialMovement;
import dac.bantads.repositories.FinancialMovementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinancialMovementService {
    @Autowired
    FinancialMovementRepository financialMovementRepository;
    @Transactional
    public FinancialMovement save(FinancialMovement financialMovement) {
        return financialMovementRepository.save(financialMovement);
    }
    @Transactional
    public void deleteById(Long id) {
        financialMovementRepository.deleteById(id);
    }
    public Optional<FinancialMovement> findById(Long id) {
        return financialMovementRepository.findById(id);
    }
    public List<FinancialMovement> findAllWhereClientId(Long clientId) {
        return financialMovementRepository.findAllWhereClientId(clientId);
    }
    public boolean existsById(Long id) {
        return financialMovementRepository.existsById(id);
    }
    @Transactional
    public void update(FinancialMovement financialMovement) {
        financialMovementRepository.update(financialMovement);
    }
}