package dac.bantads.repositories;

import dac.bantads.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdministratorRepository extends JpaRepository<Manager, UUID> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<Manager> findByEmail(String email);
    Optional<Manager> findByCpf(String cpf);
    void deleteByCpf(String cpf);
    List<Manager> findAll();
    void update(Manager manager);
}