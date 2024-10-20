package br.com.santander.creditcardservice.services.impl;

import br.com.santander.creditcardservice.clients.AccountServiceClient;
import br.com.santander.creditcardservice.models.RequestCreditCard;
import br.com.santander.creditcardservice.models.dto.Account;
import br.com.santander.creditcardservice.models.dto.ResponseCreditCard;
import br.com.santander.creditcardservice.repository.RequestCreditCardRepository;
import br.com.santander.creditcardservice.services.RequestCreditCardServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static br.com.santander.creditcardservice.models.type.Score.FAIR;
import static br.com.santander.creditcardservice.models.type.Score.GOOD;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RequestCreditCardServicesImplTest {

    private RequestCreditCardServices requestCreditCardServices;

    @Mock
    private RequestCreditCardRepository requestCreditCardRepository;
    @Mock
    private AccountServiceClient serviceClient;

    private final String CPF = "04688560052";

    @BeforeEach
    void setUp() {
        requestCreditCardServices = new RequestCreditCardServicesImpl(requestCreditCardRepository, serviceClient);
    }

    @Test
    void itShouldRequestCreditCardForYoungWhoHasJobAndDoesHaveHouseWithApproved() {
        // givin
        String clientCFP = CPF;

        // when
        Account account = getAccount();
        Mockito.when(serviceClient.findByClientCpf(clientCFP)).thenReturn(account);
        Mockito.when(requestCreditCardRepository.save(Mockito.any())).thenReturn(Mockito.any());
        ResponseCreditCard responseCreditCard = requestCreditCardServices.requestCard(clientCFP);

        // then
        assertEquals(responseCreditCard.getClientName(), account.getClientName());
        assertEquals(responseCreditCard.getResult(), "APPROVED");
    }

    @Test
    void itShouldRequestCreditCardForYoungWhoHasJobAndOwnHouseAndFairScoreWithApproved() {
        // givin
        String clientCFP = CPF;

        // when
        Account account = getAccount();
        account.setOwnHouse(true);
        account.setScore(FAIR);
        Mockito.when(serviceClient.findByClientCpf(clientCFP)).thenReturn(account);
        Mockito.when(requestCreditCardRepository.save(Mockito.any())).thenReturn(Mockito.any());
        ResponseCreditCard responseCreditCard = requestCreditCardServices.requestCard(clientCFP);

        // then
        assertEquals(responseCreditCard.getClientName(), account.getClientName());
        assertEquals(responseCreditCard.getResult(), "APPROVED");
    }

    @Test
    void itShouldRequestCreditCardForYoungWhoNoHasJobAndOwnHouseAndGoodScoreWithApproved() {
        // givin
        String clientCFP = CPF;

        // when
        Account account = getAccount();
        account.setOwnHouse(true);
        account.setHasJob(false);
        account.setScore(GOOD);
        Mockito.when(serviceClient.findByClientCpf(clientCFP)).thenReturn(account);
        Mockito.when(requestCreditCardRepository.save(Mockito.any())).thenReturn(Mockito.any());
        ResponseCreditCard responseCreditCard = requestCreditCardServices.requestCard(clientCFP);

        // then
        assertEquals(responseCreditCard.getClientName(), account.getClientName());
        assertEquals(responseCreditCard.getResult(), "APPROVED");
    }

    @Test
    void itShouldRequestCreditCardForYoungWhoNoHasJobAndNoOwnHouseAndFairScoreWithNotApproved() {
        // givin
        String clientCFP = CPF;

        // when
        Account account = getAccount();
        account.setOwnHouse(false);
        account.setHasJob(false);
        account.setScore(FAIR);
        Mockito.when(serviceClient.findByClientCpf(clientCFP)).thenReturn(account);
        Mockito.when(requestCreditCardRepository.save(Mockito.any())).thenReturn(Mockito.any());
        ResponseCreditCard responseCreditCard = requestCreditCardServices.requestCard(clientCFP);

        // then
        assertEquals(responseCreditCard.getClientName(), account.getClientName());
        assertEquals(responseCreditCard.getResult(), "NOT APPROVED");
    }

    @Test
    void itShouldRequestCreditCardForMiddleWhoHasJobOwnHouseWithApproved() {
        // givin
        String clientCFP = CPF;

        // when
        Account account = getAccount();
        account.setOwnHouse(true);
        account.setClientBirth(LocalDate.of(1970, 10, 20));
        Mockito.when(serviceClient.findByClientCpf(clientCFP)).thenReturn(account);
        Mockito.when(requestCreditCardRepository.save(Mockito.any())).thenReturn(Mockito.any());
        ResponseCreditCard responseCreditCard = requestCreditCardServices.requestCard(clientCFP);

        // then
        assertEquals(responseCreditCard.getClientName(), account.getClientName());
        assertEquals(responseCreditCard.getResult(), "APPROVED");
    }

    @Test
    void itShouldRequestCreditCardForMiddleWhoHasJobOwnHouseWithNotApproved() {
        // givin
        String clientCFP = CPF;

        // when
        Account account = getAccount();
        account.setClientBirth(LocalDate.of(1970, 10, 20));
        Mockito.when(serviceClient.findByClientCpf(clientCFP)).thenReturn(account);
        Mockito.when(requestCreditCardRepository.save(Mockito.any())).thenReturn(Mockito.any());
        ResponseCreditCard responseCreditCard = requestCreditCardServices.requestCard(clientCFP);

        // then
        assertEquals(responseCreditCard.getClientName(), account.getClientName());
        assertEquals(responseCreditCard.getResult(), "NOT APPROVED");
    }

    @Test
    void itShouldRequestCreditCardForOldWhoFairWithNotApproved() {
        // givin
        String clientCFP = CPF;

        // when
        Account account = getAccount();
        account.setScore(FAIR);
        account.setClientBirth(LocalDate.of(1950, 10, 20));
        Mockito.when(serviceClient.findByClientCpf(clientCFP)).thenReturn(account);
        Mockito.when(requestCreditCardRepository.save(Mockito.any())).thenReturn(Mockito.any());
        ResponseCreditCard responseCreditCard = requestCreditCardServices.requestCard(clientCFP);

        // then
        assertEquals(responseCreditCard.getClientName(), account.getClientName());
        assertEquals(responseCreditCard.getResult(), "NOT APPROVED");
    }

    @Test
    void itShouldRequestCreditCardForOldApproved() {
        // givin
        String clientCFP = CPF;

        // when
        Account account = getAccount();
        account.setClientBirth(LocalDate.of(1950, 10, 20));
        Mockito.when(serviceClient.findByClientCpf(clientCFP)).thenReturn(account);
        Mockito.when(requestCreditCardRepository.save(Mockito.any())).thenReturn(Mockito.any());
        ResponseCreditCard responseCreditCard = requestCreditCardServices.requestCard(clientCFP);

        // then
        assertEquals(responseCreditCard.getClientName(), account.getClientName());
        assertEquals(responseCreditCard.getResult(), "APPROVED");
    }

    @Test
    void itShouldThrowWhenAgeIsInvalid() {
        // givin
        String clientCFP = CPF;

        // when
        Account account = getAccount();
        account.setClientBirth(LocalDate.now());
        Mockito.when(serviceClient.findByClientCpf(clientCFP)).thenReturn(account);

        // then
        assertThrows(RuntimeException.class, () -> requestCreditCardServices.requestCard(clientCFP));
    }

    @Test
    void findAll() {
        requestCreditCardServices.findAll();
        Mockito.verify(requestCreditCardRepository).findAll();
    }

    private Account getAccount() {
        return Account.builder()
                .accountNumber("0000000-0")
                .clientName("Quitumba Ferreira")
                .clientCpf(CPF)
                .typeAccount("CA")
                .clientBirth(LocalDate.of(2000, 10, 20))
                .hasJob(true)
                .ownHouse(false)
                .score(GOOD)
                .createdAt(LocalDateTime.now())
                .build();
    }
}