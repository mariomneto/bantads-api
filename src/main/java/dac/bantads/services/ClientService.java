package dac.bantads.services;

import dac.bantads.models.Account;
import dac.bantads.models.Client;
import dac.bantads.repositories.ClientRepository;
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
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Transactional
    public void deleteByCpf(String cpf) {
        clientRepository.deleteByCpf(cpf);
    }
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public Optional<Client> findByCpf(String cpf) {
        return clientRepository.findByCpf(cpf);
    }
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    public boolean existsByCpf(String cpf) {
        return clientRepository.existsByCpf(cpf);
    }
    public boolean existsById(Long id) {
        return clientRepository.existsById(id);
    }
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }
    @Transactional
    public void update(Client client) {
        clientRepository.update(client);
    }
}