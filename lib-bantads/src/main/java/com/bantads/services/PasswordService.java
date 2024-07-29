package com.bantads.services;

import com.bantads.model.Password;
import com.bantads.repositories.PasswordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordService {
    @Autowired
    PasswordRepository passwordRepository;
    @Transactional
    public Password save(Password password) {
        return passwordRepository.save(password);
    }
    public Optional<Password> findByUserCpf(String userCpf) {
        return passwordRepository.findByUserCpf(userCpf);
    }
    @Transactional
    public void update(Password password) {
        passwordRepository.update(password);
    }
}