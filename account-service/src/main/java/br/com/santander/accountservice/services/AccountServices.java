package br.com.santander.accountservice.services;

import br.com.santander.accountservice.models.dto.AccountDto;
import br.com.santander.accountservice.models.dto.AccountInputDto;
import br.com.santander.accountservice.models.entities.Account;

public interface AccountServices {

    AccountDto save(AccountInputDto accountInputDto);
    Account findByCpf(String cpf);
}
