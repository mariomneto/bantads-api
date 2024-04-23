package dac.bantads.controllers;

import dac.bantads.dtos.AccountClientDto;
import dac.bantads.dtos.ManagerAccountApprovalDto;
import dac.bantads.enums.AccountApprovalStatus;
import dac.bantads.models.Account;
import dac.bantads.models.Client;
import dac.bantads.services.AccountService;
import dac.bantads.services.ClientService;
import dac.bantads.services.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gerentes")
public class ManagerController {
    @Autowired
    final ManagerService managerService;
    @Autowired
    final AccountService accountService;
    @Autowired
    final ClientService clientService;

    @GetMapping("{managerId}/contas-pendentes")
    public ResponseEntity<Object> manager(@PathVariable(value = "managerId") Long managerId)
            throws InterruptedException, ExecutionException {
        var manager = managerService.findById(managerId).get();
        List<AccountClientDto> accountsAndClients = new ArrayList<>();
        for (Long accountId : manager.getAccountIds()) {
            var account = accountService.findById(accountId).get();
            if (account.getAccountApproval().getAccountApprovalStatus() == AccountApprovalStatus.PENDING_APPROVAL) {
                var client = clientService.findById(account.getClientId()).get();
                var accountClient = new AccountClientDto(client, account);
                accountsAndClients.add(accountClient);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(accountsAndClients);
    }

    @GetMapping("{managerId}/clientes")
    public ResponseEntity<Object> searchClients(@PathVariable(value = "managerId") Long managerId,
            @RequestParam("data") String name, @RequestParam("data") String cpf)
            throws InterruptedException, ExecutionException {
        var manager = managerService.findById(managerId).get();
        List<AccountClientDto> accountsAndClients = new ArrayList<>();

        if(name != null || cpf != null) {
            for (Long accountId : manager.getAccountIds()) {
                var optionalClient = name != null ? clientService.findByNameContaining(name) : clientService.findByCpfContaining(cpf);
                if(!optionalClient.isEmpty()) {
                    var client = optionalClient.get();
                    var account = accountService.findByClientId(client.getId()).get();
                    var accountClient = new AccountClientDto(client, account);
                    accountsAndClients.add(accountClient);
                }
            }
        }
        else {
            for (Long accountId : manager.getAccountIds()) {
                var account = accountService.findById(accountId).get();
                var client = clientService.findById(account.getClientId()).get();
                var accountClient = new AccountClientDto(client, account);
                accountsAndClients.add(accountClient);
            }
        }

        // todo ordenar por nome dos clientes
        return ResponseEntity.status(HttpStatus.OK).body(accountsAndClients);
    }

    @GetMapping("{managerId}/melhores-clientes")
    public ResponseEntity<Object> bestClients(@PathVariable(value = "managerId") Long managerId) {
        List<Client> clients = clientService.findTop5ByOrderByBalanceDesc();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @PostMapping("{managerId}/avaliar-conta/{accountId}")
    public ResponseEntity<Object> approveAccount(@PathVariable(value = "managerId") Long managerId,
                                                 @PathVariable(value = "accountId") Long accountId,
                                                 @RequestBody @Valid ManagerAccountApprovalDto approvalDto) {
        var optionalAccount = accountService.findById(accountId);
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta não existe.");
        }
        var account = optionalAccount.get();
        if (account.getManagerId() != managerId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Esta conta não é responsabilidade deste gerente.");
        }
        account.getAccountApproval().setAccountApprovalStatus(
                approvalDto.isApproved() ? AccountApprovalStatus.APPROVED : AccountApprovalStatus.REJECTED);
        try {
            accountService.update(account);
            // todo: mandar email para cliente com senha
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um erro. A conta não foi avaliada.");
        }

    }
}
