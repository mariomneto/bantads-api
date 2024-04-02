package dac.bantads.repositories;

import dac.bantads.models.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordRepository extends JpaRepository<Password, UUID> {
    Optional<Password> findByUserCpf(String userCpf);
    void update(Password password);
}