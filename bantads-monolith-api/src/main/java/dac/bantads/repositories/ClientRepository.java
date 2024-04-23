package dac.bantads.repositories;

import dac.bantads.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByCpf(String cpf);
    boolean existsById(Long id);
    boolean existsByEmail(String email);
    List<Client> findAll();
    Optional<Client> findByCpf(String cpf);
    Optional<Client> findByCpfContaining(String cpf);
    Optional<Client> findByNameContaining(String name);
    Optional<Client> findById(Long id);
    Optional<Client> findByEmail(String email);
    void deleteByCpf(String cpf);
    void deleteById(Long id);
    void update(Client client);
    @Query("SELECT c FROM client c ORDER BY c.balance DESC")
    List<Client> findTop5ByOrderByBalanceDesc();
}