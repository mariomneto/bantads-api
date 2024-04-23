package dac.bantads.dtos;

import dac.bantads.models.Account;
import dac.bantads.models.Client;
import dac.bantads.models.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientAccountManagerDto {
    private Client client;
    private Account account;
    private Manager manager;
}
