package dac.bantads.controllers;

import dac.bantads.dtos.ClientRegisterDto;
import dac.bantads.dtos.UserLoginDto;
import dac.bantads.enums.AccountApprovalStatus;
import dac.bantads.models.*;
import dac.bantads.services.AdministratorService;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    final ClientService clientService;
    @Autowired
    final ManagerService managerService;
    @Autowired
    final AdministratorService administratorService;
    @Autowired
    final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    final PasswordService passwordService;
//    @Autowired
//    final CustomAuthenticationProvider authenticationProvider;

    @PostMapping("/registrar")
    public ResponseEntity<Object> register(@RequestBody @Valid ClientRegisterDto clientRegisterDto) {

        //cada cliente só uma conta
        if (clientService.existsByCpf(clientRegisterDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cliente com esse cpf já existe.");
        }
        try {
            //ao cadastrar criar uma conta automaticamente
            var client = storeNewClient(clientRegisterDto);
            var account = storeNewAccount(clientRegisterDto);
            if(account.isEmpty()){
                //todo: enviar email para cliente
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar conta(1).");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(client);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar conta(2).");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        var client = clientService.findByEmail(userLoginDto.getEmail());
        if(!client.isEmpty()){
            //todo autenticação
            return ResponseEntity.status(HttpStatus.OK).body(client);
        }
        var manager = managerService.findByEmail(userLoginDto.getEmail());
        if(!manager.isEmpty()){
            //todo autenticação
            return ResponseEntity.status(HttpStatus.OK).body(manager);
        }
        var administrator = administratorService.findByEmail(userLoginDto.getEmail());
        if(!administrator.isEmpty()){
            //todo autenticação
            return ResponseEntity.status(HttpStatus.OK).body(administrator);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta não encontrada.");
    }

    private Optional<Client> storeNewClient(ClientRegisterDto clientRegisterDto) {
        var client = new Client();
        BeanUtils.copyProperties(clientRegisterDto, client);
        Client newClient;
        try {
            newClient = clientService.save(client);
        } catch (Exception e) {
            return Optional.empty();
        }
        //tabela separada com pares <userId, senha>
        var password = new Password();
        password.setUserId(newClient.getCpf());
        password.setPassword(passwordEncoder.encode(clientRegisterDto.getPassword()));
        passwordService.save(password);
        return Optional.of(newClient);
    }

    private Optional<Account> storeNewAccount(ClientRegisterDto clientRegisterDto) {
        var account = new Account();
        account.setClientCpf(clientRegisterDto.getCpf());
        if(clientRegisterDto.getMonthlySalary() > 2000){
            account.setLimit(clientRegisterDto.getMonthlySalary() / 2);
        }

        List<Manager> managers = managerService.findAll();
        int minCount = Integer.MAX_VALUE;
        Optional<Manager> assignedManager = Optional.empty();
        for (Manager manager : managers) {
            int count = manager.getClientCpfs().size();
            if (count < minCount) {
                //designar gerente
                minCount = count;
                assignedManager = Optional.of(manager);
            }
        }
        //se faltar gerente processo falha
        if(assignedManager.isEmpty()){
            return Optional.empty();
        }

        account.setManagerCpf(assignedManager.get().getCpf());

        var accountApproval = new AccountApproval();
        accountApproval.setAccountApprovalStatus(AccountApprovalStatus.PENDING_APPROVAL);
        account.setAccountApproval(accountApproval);
        //todo: salvar cpf do novo cliente no gerente

        return Optional.of(account);
    }
}
