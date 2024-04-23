package dac.bantads.dtos;

import dac.bantads.models.Account;
import dac.bantads.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccountClientDto {
    private Client client;
    private Account account;
}
