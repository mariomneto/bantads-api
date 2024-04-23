package dac.bantads.services;

import dac.bantads.models.Account;
import dac.bantads.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }
    @Transactional
    public Optional<Account> findByClientId(Long id) {
        return accountRepository.findByClientId(id);
    }
    public List<Account> findByManagerId(Long id) {
        return accountRepository.findByManagerId(id);
    }
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }
    public boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }
    public boolean existsByClientCpf(String cpf) {
        return accountRepository.existsByClientCpf(cpf);
    }
    @Transactional
    public void update(Account account) {
        accountRepository.update(account);
    }
}
