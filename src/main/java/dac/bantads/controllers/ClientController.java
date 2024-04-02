package dac.bantads.controllers;

import dac.bantads.dtos.*;
import dac.bantads.services.AccountService;
import dac.bantads.services.ClientService;
import dac.bantads.services.FinancialMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClientController {
    @Autowired
    final AccountService accountService;
    @Autowired
    final ClientService clientService;
    @Autowired
    final FinancialMovementService financialMovementService;
    @PostMapping("{id}/depositar")
    public ResponseEntity<Object> deposit(@PathVariable(value = "id") Long id, @RequestBody @Valid DepositDto depositDto) throws InterruptedException, ExecutionException {
        var account = accountService.findById(depositDto.getAccountId()).get();
        account.setBalance(account.getBalance() + depositDto.getValue());
        accountService.update(account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
    @PostMapping("{id}/sacar")
    public ResponseEntity<Object> deposit(@PathVariable(value = "id") Long id, @RequestBody @Valid WithdrawalDto withdrawalDto) throws InterruptedException, ExecutionException {
        var account = accountService.findById(withdrawalDto.getAccountId()).get();
        //checar saldo
        if(account.getBalance() < withdrawalDto.getValue()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo indisponível.");
        }
        account.setBalance(account.getBalance() - withdrawalDto.getValue());
        accountService.update(account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
    @PostMapping("{id}/transferir")
    public ResponseEntity<Object> transfer(@PathVariable(value = "id") Long id, @RequestBody @Valid TransferDto transferDto) throws InterruptedException, ExecutionException {
        var originAccount = accountService.findById(transferDto.getOriginAccountId()).get();
        var destinationAccount = accountService.findById(transferDto.getDestinationAccountId()).get();
        //checar saldo da origem
        if(originAccount.getBalance() < transferDto.getValue()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo indisponível na conta de origem.");
        }
        destinationAccount.setBalance(destinationAccount.getBalance() + transferDto.getValue());
        originAccount.setBalance(originAccount.getBalance() - transferDto.getValue());
        accountService.update(originAccount);
        accountService.update(destinationAccount);
        return ResponseEntity.status(HttpStatus.OK).body(originAccount);
    }
    @PostMapping("{id}/historico")
    public ResponseEntity<Object> history(@PathVariable(value = "id") Long id, @RequestBody @Valid BankStatementDto bankStatementDto) throws InterruptedException, ExecutionException {
        var history = financialMovementService.findAllWhereClientId(id);
        return ResponseEntity.status(HttpStatus.OK).body(history);
    }
    @PostMapping("{id}/atualizar")
    public ResponseEntity<Object> updateClient(@PathVariable(value = "id") Long id, @RequestBody @Valid ClientUpdateDto clientUpdateDto) throws InterruptedException, ExecutionException {
        var client = clientService.findById(id).get();
        var account = accountService.findByClientId(id).get();
        var newSalary = clientUpdateDto.getSalary();
        if(newSalary != client.getSalary()){
            //limite metade do salario
            var newLimit = newSalary / 2;
            if(newSalary < 2000) {
                //zerar limite se abaixo de 2k
                newLimit=0;
            }
            //se saldo for maior que limite, limite é saldo
            account.setLimit(newLimit > account.getBalance() ? newLimit : account.getBalance());
        }
        clientService.update(client);
        accountService.update(account);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }
}
