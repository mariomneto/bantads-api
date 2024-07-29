package com.bantads.model.dtos;

import com.bantads.model.Account;
import com.bantads.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccountClientDto {
    private Client client;
    private Account account;
}
