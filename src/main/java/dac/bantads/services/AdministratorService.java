package dac.bantads.services;

import dac.bantads.models.Manager;
import dac.bantads.repositories.AdministratorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministratorService {
    @Autowired
    AdministratorRepository managerRepository;
    @Transactional
    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }
    @Transactional
    public Optional<Manager> findByEmail(String email) {
        return managerRepository.findByEmail(email);
    }
    public boolean existsByEmail(String email) {
        return managerRepository.existsByEmail(email);
    }
    public List<Manager> findAll(){
        return managerRepository.findAll();
    }
    public void update(Manager manager) {
        managerRepository.update(manager);
    }
}