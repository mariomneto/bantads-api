package com.mp.bantads.repositories;

import com.mp.bantads.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    boolean existsByCpf(String cpf);
    Optional<Manager> findByCpf(String cpf);
}