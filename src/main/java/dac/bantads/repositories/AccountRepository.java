package dac.bantads.repositories;

import dac.bantads.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByClientCpf(String cpf);
    boolean existsByAccountNumber(Long accountNumber);
    Optional<Account> findByAccountNumber(Long accountNumber);
    Optional<Account> findByClientCpf(String cpf);
    Optional<Account> findByClientId(Long id);
    void deleteById(Long id);
    void deleteByClientCpf(String cpf);
    void update(Account account);
}