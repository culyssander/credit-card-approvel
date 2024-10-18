package br.com.santander.creditcardservice.services.impl;

import br.com.santander.creditcardservice.clients.AccountServiceClient;
import br.com.santander.creditcardservice.controller.RequestCreditCardController;
import br.com.santander.creditcardservice.models.RequestCreditCard;
import br.com.santander.creditcardservice.models.dto.Account;
import br.com.santander.creditcardservice.models.dto.ResponseCreditCard;
import br.com.santander.creditcardservice.models.logs.EventLog;
import br.com.santander.creditcardservice.models.logs.LoggerType;
import br.com.santander.creditcardservice.models.type.Score;
import br.com.santander.creditcardservice.repository.RequestCreditCardRepository;
import br.com.santander.creditcardservice.services.RequestCreditCardServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static br.com.santander.creditcardservice.util.Constants.EVENT_CREDIT_CARD_REQUEST_START_EXECUTION;
import static br.com.santander.creditcardservice.util.Constants.KEY_FIELD_CREDIT_CARD_REQUEST;

@Slf4j
@Service
public class RequestCreditCardServicesImpl implements RequestCreditCardServices {

    private RequestCreditCardRepository repository;

    private AccountServiceClient serviceClient;

    public RequestCreditCardServicesImpl(RequestCreditCardRepository repository, AccountServiceClient serviceClient) {
        this.repository = repository;
        this.serviceClient = serviceClient;
    }

    @Override
    public ResponseCreditCard requestCard(String clientCPF) {
        Account account = serviceClient.findByClientCpf(clientCPF);
        EventLog.builder()
                .event(EVENT_CREDIT_CARD_REQUEST_START_EXECUTION)
                .message(RequestCreditCardServicesImpl.class.getMethods()[0].getName())
                .field(KEY_FIELD_CREDIT_CARD_REQUEST, account)
                .build()
                .printLog(log, LoggerType.INFO);

        return validator(account);
    }

    private ResponseCreditCard validator(Account account) {
        Integer age = age(account.getClientBirth());
        boolean isApproved;

        if (age >= 18 && age <= 39) {
            isApproved = validateYoung(account);
        } else if (age >= 40 && age <= 59) {
            isApproved = validateMiddlePeople(account);
        } else if (age >= 60 && age <= 100) {
            isApproved = validateOld(account);
        } else {
            throw new RuntimeException("Invalid age: " + age);
        }

        RequestCreditCard request = RequestCreditCard.builder()
                .clientCpf(account.getClientCpf())
                .score(account.getScore().getValue())
                .result("APPROVED")
                .hasJob(account.isHasJob())
                .ownHouse(account.isOwnHouse())
                .createdAt(LocalDateTime.now())
                .build();

        if (!isApproved) {
            request.setResult("NOT APPROVED");
            request.setReason("Need to increase score");
        }

        save(request);

        return ResponseCreditCard.builder()
                .clientName(account.getClientName())
                .result(request.getResult())
                .reason(request.getReason())
                .createdAt(request.getCreatedAt())
                .build();
    }

    private boolean validateMiddlePeople(Account account) {

        if (account.isHasJob() && account.isOwnHouse() && !account.getScore().equals(Score.FAIR)) {
            return true;
        }

        return false;
    }

    private boolean validateYoung(Account account) {
        if (account.isHasJob() && account.isOwnHouse()) {
            return true;
        } else if (account.isOwnHouse() && !account.getScore().equals(Score.FAIR)) {
            return true;
        } else if (account.isHasJob() && !account.getScore().equals(Score.FAIR)) {
            return true;
        }

        return false;
    }

    private boolean validateOld(Account account) {

        if (!account.getScore().equals(Score.FAIR)) {
            return true;
        }
        return false;
    }



    private int age(final LocalDate aniversario) {
        return Period.between(aniversario, LocalDate.now()).getYears();
    }

    private void save(RequestCreditCard requestCreditCard) {
        repository.save(requestCreditCard);
    }

    public List<RequestCreditCard> findAll() {
        return repository.findAll();
    }
}
