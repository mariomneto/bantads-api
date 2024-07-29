package com.bantads.model.dtos;

import com.bantads.model.Account;
import com.bantads.model.Client;
import com.bantads.model.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientAccountManagerDto {
    private Client client;
    private Account account;
    private Manager manager;
}
