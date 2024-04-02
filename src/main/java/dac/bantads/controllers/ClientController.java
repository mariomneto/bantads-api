package dac.bantads.controllers;

import dac.bantads.dtos.ClientUpdateDto;
import dac.bantads.dtos.ManagerAccountApprovalDto;
import dac.bantads.models.Client;
import dac.bantads.services.AccountService;
import dac.bantads.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    @PostMapping("depositar")
    public ResponseEntity<Object> deposit(@PathVariable(value = "cpf") String cpf, @RequestBody @Valid ClientUpdateDto clientUpdateDto) throws InterruptedException, ExecutionException {
    }
    @PostMapping("atualizar/{cpf}")
    public ResponseEntity<Object> updateClient(@PathVariable(value = "cpf") String cpf, @RequestBody @Valid ClientUpdateDto clientUpdateDto) throws InterruptedException, ExecutionException {
        var client = clientService.findByCpf(cpf).get();
        var account = accountService.findByClientCpf(cpf).get();
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
