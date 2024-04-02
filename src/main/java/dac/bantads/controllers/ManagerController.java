package dac.bantads.controllers;

import dac.bantads.dtos.ManagerAccountApprovalDto;
import dac.bantads.enums.AccountApprovalStatus;
import dac.bantads.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gerente")
public class ManagerController {
    @Autowired
    final AccountService accountService;
    @PostMapping("avaliar-conta")
    public ResponseEntity<Object> approveAccount(@RequestBody @Valid ManagerAccountApprovalDto approvalDto) {
        //todo: id da conta a ser avaliada passado na url
        var optionalAccount = accountService.findById(approvalDto.getAccountId());
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
