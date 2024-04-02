package dac.bantads.dtos;

import dac.bantads.models.Account;
import dac.bantads.models.Client;
import lombok.Data;

@Data
public class ClientAccountDto {
    private Account account;
    private Client client;
}
