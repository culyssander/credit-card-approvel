package br.com.santander.accountservice.controller;

import br.com.santander.accountservice.models.dto.AccountDto;
import br.com.santander.accountservice.models.dto.AccountInputDto;
import br.com.santander.accountservice.models.entities.Account;
import br.com.santander.accountservice.models.logs.EventLog;
import br.com.santander.accountservice.models.logs.LoggerType;
import br.com.santander.accountservice.services.AccountServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static br.com.santander.accountservice.util.Constants.*;

@Slf4j
@RestController
@RequestMapping("api/v1/accounts")
@Tag(name = "Users", description = "Users Service API")
public class AccountController {

    @Autowired
    private AccountServices accountServices;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@Validated @RequestBody AccountInputDto accountInputDto, @RequestHeader(required = false, defaultValue = "3243455456466") String correlationId) {
        MDC.put(CORRELACTION_ID, correlationId);
        EventLog.builder()
                .event(EVENT_CREATE_NEW_ACCOUNT_START_EXECUTION)
                .message(AccountController.class.getMethods()[0].getName())
                .field(KEY_FIELD_CREATE_NEW_ACCOUNT, accountInputDto)
                .build()
                .printLog(log, LoggerType.INFO);

        AccountDto account = accountServices.save(accountInputDto);

        EventLog.builder()
                .event(EVENT_CREATE_NEW_ACCOUNT_END_EXECUTION)
                .message(AccountController.class.getMethods()[0].getName())
                .field(KEY_FIELD_CREATE_NEW_ACCOUNT, account)
                .build()
                .printLog(log, LoggerType.INFO);
        return account;
    }

    @GetMapping("/cpf/{cpf}")
    public Account findByCPF(@PathVariable String cpf) {
        return accountServices.findByCpf(cpf);
    }
}
