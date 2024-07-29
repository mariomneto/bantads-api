package com.bantads.model.dtos;

import com.bantads.model.Account;
import com.bantads.model.Client;
import lombok.Data;

@Data
public class ClientAccountDto {
    private Account account;
    private Client client;
}
