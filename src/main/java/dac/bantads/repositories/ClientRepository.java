package dac.bantads.repositories;

import dac.bantads.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<Client> findByCpf(String cpf);
    Optional<Client> findByEmail(String email);
    void deleteByCpf(String cpf);
    void update(Client client);
}