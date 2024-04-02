package dac.bantads.services;

import dac.bantads.models.Manager;
import dac.bantads.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;
    @Transactional
    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }
    @Transactional
    public void deleteByCpf(String cpf) {
        managerRepository.deleteByCpf(cpf);
    }
    public Optional<Manager> findByCpf(String cpf) {
        return managerRepository.findByCpf(cpf);
    }
    public Optional<Manager> findByEmail(String email) {
        return managerRepository.findByEmail(email);
    }
    public boolean existsByCpf(String cpf) {
        return managerRepository.existsByCpf(cpf);
    }
    public boolean existsByEmail(String email) {
        return managerRepository.existsByEmail(email);
    }
    public List<Manager> findAll(){
        return managerRepository.findAll();
    }
    @Transactional
    public void update(Manager manager) {
        managerRepository.update(manager);
    }
}