package dac.bantads.controllers;

import dac.bantads.dtos.*;
import dac.bantads.enums.AccountApprovalStatus;
import dac.bantads.models.*;
import dac.bantads.services.AccountService;
import dac.bantads.services.ClientService;
import dac.bantads.services.ManagerService;
import dac.bantads.services.PasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdministratorController {
    @Autowired
    final ManagerService managerService;
    @Autowired
    final AccountService accountService;
    @Autowired
    final ClientService clientService;
    @Autowired
    final PasswordService passwordService;
    @Autowired
    final BCryptPasswordEncoder passwordEncoder;
    @GetMapping
    public ResponseEntity<Object> home() throws InterruptedException, ExecutionException {
        var managers = managerService.findAll();
        List<ManagerPerformanceDto> managersAndPerformances = new ArrayList<>();

        for (var manager : managers) {
            var accounts = accountService.findByManagerId(manager.getId());
            int totalAccounts = manager.getAccountIds().size();
            double totalPositiveBalance = accounts.stream()
                    .filter(account -> account.getBalance() > 0)
                    .mapToDouble(Account::getBalance)
                    .sum();
            double totalNegativeBalance = accounts.stream()
                    .filter(account -> account.getBalance() < 0)
                    .mapToDouble(Account::getBalance)
                    .sum();
            var managerAndPerformance =
                    new ManagerPerformanceDto(manager, totalAccounts, totalPositiveBalance, totalNegativeBalance);
            managersAndPerformances.add(managerAndPerformance);
        }
        return ResponseEntity.status(HttpStatus.OK).body(managersAndPerformances);
    }

    @GetMapping("clientes") ResponseEntity<Object> allClients() throws InterruptedException, ExecutionException {
        //todo paginação
        //todo ordenar por nome
        var clients = clientService.findAll();
        List<ClientAccountManagerDto> clientData = new ArrayList<>();
        for(var client : clients) {
            var account = accountService.findByClientId(client.getId()).get();
            var manager = managerService.findById(account.getManagerId()).get();
            var clientAccountManager = new ClientAccountManagerDto(client, account, manager);
            clientData.add(clientAccountManager);
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientData);
    }

    @GetMapping("gerentes") ResponseEntity<Object> allManagers() throws InterruptedException, ExecutionException {
        //todo paginação
        //todo ordenar por nome
        var managers = managerService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(managers);
    }

    @PostMapping("gerentes") ResponseEntity<Object> createManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) throws InterruptedException, ExecutionException {
        if (managerService.existsByCpf(managerCreateDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Gerente com esse cpf já existe.");
        }
        var manager = storeNewManager(managerCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(manager);
    }

    @PutMapping("gerentes/{managerId}") ResponseEntity<Object> updateManager(@PathVariable(value = "managerId") Long managerId, @RequestBody @Valid ManagerUpdateDto managerUpdateDto) throws InterruptedException, ExecutionException {
        var manager = managerService.findById(managerId).get();
        BeanUtils.copyProperties(managerUpdateDto, manager);
        managerService.update(manager);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    private Optional<Manager> storeNewManager(ManagerCreateDto managerCreateDto) throws InterruptedException, ExecutionException {
        var manager = new Manager();
        BeanUtils.copyProperties(managerCreateDto, manager);
        Manager newManager;
        try {
            newManager = managerService.save(manager);
        } catch (Exception e) {
            return Optional.empty();
        }
        //tabela separada com pares <managerId, senha>
        var password = new Password();
        password.setUserId(newManager.getId());
        password.setPassword(passwordEncoder.encode(managerCreateDto.getPassword()));
        passwordService.save(password);
        return Optional.of(newManager);
    }
}
