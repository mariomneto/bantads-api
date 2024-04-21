package dac.bantads.controllers;

import dac.bantads.dtos.*;
import dac.bantads.enums.FinancialMovementType;
import dac.bantads.models.Deposit;
import dac.bantads.models.Transfer;
import dac.bantads.models.Withdrawal;
import dac.bantads.services.AccountService;
import dac.bantads.services.ClientService;
import dac.bantads.services.FinancialMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    final AccountService accountService;
    @Autowired
    final ClientService clientService;
    @Autowired
    final FinancialMovementService financialMovementService;
    @GetMapping("{id}")
    public ResponseEntity<Object> client(@PathVariable(value = "id") Long id) throws InterruptedException, ExecutionException {
        var client = clientService.findById(id).get();
        var account = accountService.findById(id).get();
        var accountClient = new AccountClientDto(client, account);
        return ResponseEntity.status(HttpStatus.OK).body(accountClient);
    }
    @PostMapping("{id}/depositar")
    public ResponseEntity<Object> deposit(@PathVariable(value = "id") Long id, @RequestBody @Valid DepositDto depositDto) throws InterruptedException, ExecutionException {
        var account = accountService.findById(depositDto.getAccountId()).get();
        account.setBalance(account.getBalance() + depositDto.getValue());
        accountService.update(account);
        saveDeposit(depositDto);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
    @PostMapping("{id}/sacar")
    public ResponseEntity<Object> withdrawal(@PathVariable(value = "id") Long id, @RequestBody @Valid WithdrawalDto withdrawalDto) throws InterruptedException, ExecutionException {
        var account = accountService.findById(withdrawalDto.getAccountId()).get();
        //checar saldo
        if(account.getBalance() < withdrawalDto.getValue()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo indisponível.");
        }
        account.setBalance(account.getBalance() - withdrawalDto.getValue());
        accountService.update(account);
        saveWithdrawal(withdrawalDto);
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
        saveTransfer(transferDto);
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
    private void saveDeposit(DepositDto depositDto) {
        var deposit = new Deposit();
        deposit.setType(FinancialMovementType.DEPOSIT);
        deposit.setDate(new Date());
        deposit.setAccountId(depositDto.getAccountId());
        deposit.setValue(depositDto.getValue());
        financialMovementService.save(deposit);
    }
    private void saveWithdrawal(WithdrawalDto withdrawalDto) {
        var withdrawal = new Withdrawal();
        withdrawal.setType(FinancialMovementType.WITHDRAWAL);
        withdrawal.setDate(new Date());
        withdrawal.setAccountId(withdrawalDto.getAccountId());
        withdrawal.setValue(withdrawalDto.getValue());
        financialMovementService.save(withdrawal);
    }
    private void saveTransfer(TransferDto transferDto) {
        var transfer = new Transfer();
        transfer.setType(FinancialMovementType.TRANSFER);
        transfer.setDate(new Date());
        transfer.setOriginAccountId(transferDto.getOriginAccountId());
        transfer.setDestinationAccountId(transferDto.getDestinationAccountId());
        transfer.setValue(transferDto.getValue());
        financialMovementService.save(transfer);
    }
}
