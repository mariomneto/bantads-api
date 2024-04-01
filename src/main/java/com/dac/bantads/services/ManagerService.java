package com.mp.bantads.services;

import com.mp.bantads.models.Manager;
import com.mp.bantads.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Manager {
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


    public boolean existsByCpf(String cpf) {
        return managerRepository.existsByCpf(cpf);
    }
}