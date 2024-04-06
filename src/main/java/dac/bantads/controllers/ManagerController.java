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
@RequestMapping("/gerente")
public class ManagerController {
    @Autowired
    final ManagerService managerService;
    @Autowired
    final AccountService accountService;
    @Autowired
    final ClientService clientService;

    @GetMapping("{id}/contas-pendentes")
    public ResponseEntity<Object> manager(@PathVariable(value = "id") Long id) throws InterruptedException, ExecutionException {
        var manager = managerService.findById(id).get();
        List<AccountClientDto> accountsAndClients = new ArrayList<>();
        for(Long accountId: manager.getAccountIds()) {
            var account = accountService.findById(accountId).get();
            if (account.getAccountApproval().getAccountApprovalStatus() == AccountApprovalStatus.PENDING_APPROVAL){
                var client = clientService.findById(account.getClientId()).get();
                var accountClient = new AccountClientDto(client, account);
                accountsAndClients.add(accountClient);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(accountsAndClients);
    }

    @GetMapping("{id}/pesquisar-cliente")
    public ResponseEntity<Object> searchClients(@PathVariable(value = "id") Long id) throws InterruptedException, ExecutionException {
        var manager = managerService.findById(id).get();
        List<AccountClientDto> accountsAndClients = new ArrayList<>();
        for(Long accountId: manager.getAccountIds()) {
            var account = accountService.findById(accountId).get();
            var client = clientService.findById(account.getClientId()).get();
            var accountClient = new AccountClientDto(client, account);
            accountsAndClients.add(accountClient);
        }
        //todo ordenar por nome dos clientes
        return ResponseEntity.status(HttpStatus.OK).body(accountsAndClients);
    }

    @PostMapping("avaliar-conta/{accountId}")
    public ResponseEntity<Object> approveAccount(@PathVariable(value = "accountId") Long accountId, @RequestBody @Valid ManagerAccountApprovalDto approvalDto) {
        var optionalAccount = accountService.findById(accountId);
        if(optionalAccount.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta não existe.");
        }
        var account = optionalAccount.get();
        account.getAccountApproval().setAccountApprovalStatus(approvalDto.isApproved() ?
                AccountApprovalStatus.APPROVED : AccountApprovalStatus.REJECTED);
        try {
            accountService.update(account);
            //todo: mandar email para cliente com senha
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Houve um erro. A conta não foi avaliada.");
        }

    }
}
