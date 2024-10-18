package br.com.santander.accountservice.services.impl;

import br.com.santander.accountservice.controller.AccountController;
import br.com.santander.accountservice.models.dto.AccountDto;
import br.com.santander.accountservice.models.dto.AccountInputDto;
import br.com.santander.accountservice.models.dto.AddressDto;
import br.com.santander.accountservice.models.entities.Account;
import br.com.santander.accountservice.models.entities.Address;
import br.com.santander.accountservice.exception.BadRequestException;
import br.com.santander.accountservice.exception.EntityNotFoundException;
import br.com.santander.accountservice.models.logs.EventLog;
import br.com.santander.accountservice.models.logs.LoggerType;
import br.com.santander.accountservice.repository.AccountRepository;
import br.com.santander.accountservice.services.AccountServices;
import br.com.santander.accountservice.services.AddressServices;
import br.com.santander.accountservice.util.AccountNumberCodeGenerator;
import br.com.santander.accountservice.util.RandomScore;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.com.santander.accountservice.util.Constants.*;


@Slf4j
@Service
public class AccountServicesImpl implements AccountServices {

    private AccountRepository accountRepository;
    private AccountNumberCodeGenerator codeGenerator;
    private ModelMapper modelMapper;
    private RandomScore randomScore;
    private AddressServices addressServices;

    public AccountServicesImpl(AccountRepository accountRepository,
                               AddressServices addressServices, AccountNumberCodeGenerator codeGenerator,
                               ModelMapper modelMapper, RandomScore randomScore) {

        this.accountRepository = accountRepository;
        this.addressServices = addressServices;
        this.codeGenerator = codeGenerator;
        this.modelMapper = modelMapper;
        this.randomScore = randomScore;
    }

    @Override
    public AccountDto save(AccountInputDto accountInputDto) {

        if (accountRepository.existsByClientCpf(accountInputDto.getClientCPF())) {
            BadRequestException exception = new BadRequestException(CPF_ALREADY_EXISTS);

            EventLog.builder()
                    .event(EVENT_CREATE_NEW_ACCOUNT_ERROR)
                    .message(exception.toString())
                    .field(KEY_FIELD_CREATE_NEW_ACCOUNT, accountInputDto)
                    .build()
                    .printLog(log, LoggerType.INFO);

            throw exception;
        }
        
        Account account = modelMapper.map(accountInputDto, Account.class);

        account.setAccountNumber(codeGenerator.generate());
        account.setScore(randomScore.score());
        account.setCreatedAt(LocalDateTime.now());
        account = accountRepository.save(account);

        accountInputDto.getAddress().setAccountNumber(account.getAccountNumber());
        Address address = addressServices.save(accountInputDto.getAddress());

        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        accountDto.setAddress(modelMapper.map(address, AddressDto.class));

        return accountDto;
    }

    @Override
    public Account findByCpf(String clientCpf) {
        return accountRepository.findByClientCpf(clientCpf)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_NOT_FOUND));
    }

}
