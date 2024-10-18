package br.com.santander.accountservice.services.impl;

import br.com.santander.accountservice.exception.BadRequestException;
import br.com.santander.accountservice.exception.EntityNotFoundException;
import br.com.santander.accountservice.models.dto.AccountDto;
import br.com.santander.accountservice.models.dto.AccountInputDto;
import br.com.santander.accountservice.models.dto.AddressDto;
import br.com.santander.accountservice.models.dto.AddressInputDto;
import br.com.santander.accountservice.models.entities.Account;
import br.com.santander.accountservice.models.entities.Address;
import br.com.santander.accountservice.models.type.Score;
import br.com.santander.accountservice.models.type.TypeAccount;
import br.com.santander.accountservice.repository.AccountRepository;
import br.com.santander.accountservice.services.AccountServices;
import br.com.santander.accountservice.services.AddressServices;
import br.com.santander.accountservice.util.AccountNumberCodeGenerator;
import br.com.santander.accountservice.util.RandomScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServicesImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AccountNumberCodeGenerator codeGenerator;

    @Mock
    private RandomScore randomScore;

    @Mock
    private AddressServices addressServices;

    private AccountServices accountServices;

    private final String ACCOUNT_NUMBER = "046885-9";
    private final String CEP = "01001-000";
    private final String CPF = "04688560052";

    @BeforeEach
    void setup() {
        accountServices = new AccountServicesImpl(accountRepository, addressServices,
                codeGenerator, modelMapper, randomScore);
    }

    @Test
    void itShouldCreateNewAccount() {
        // given
        AccountInputDto inputDto = getAccountInputDto();

        // when
        Account account = getAccount();
        Mockito.when(codeGenerator.generate()).thenReturn(ACCOUNT_NUMBER);
        Mockito.when(modelMapper.map(inputDto, Account.class)).thenReturn(account);
        Mockito.when(modelMapper.map(account, AccountDto.class)).thenReturn(getAccountDto());
        Mockito.when(modelMapper.map(new Address(), AddressDto.class)).thenReturn(getAddressDto());
        BDDMockito.given(addressServices.save(Mockito.any())).willReturn(new Address());
        Mockito.when(accountRepository.save(account)).thenReturn(account);


        accountServices.save(inputDto);
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        Mockito.verify(accountRepository).save(captor.capture());

        Account accountCapture = captor.getValue();

        // then
        assertEquals(inputDto.getClientCPF(), accountCapture.getClientCpf());
        assertEquals(inputDto.getClientName(), accountCapture.getClientName());
        assertEquals(inputDto.isHasJob(), accountCapture.isHasJob());
    }

    @Test
    void itShouldRetornClientCPFAlreadyExistsWhenCreate() {
        // given
        AccountInputDto inputDto = getAccountInputDto();
        BDDMockito.given(accountRepository.existsByClientCpf(Mockito.anyString())).willReturn(true);

        // when
        // then
        assertThrows(BadRequestException.class, () -> accountServices.save(inputDto));
    }

    @Test
    void itShouldRetornAccountWhenFindByClientCPF() {
        // given
        String clientCFP = CPF;
        BDDMockito.given(accountRepository.findByClientCpf(Mockito.anyString())).willReturn(Optional.of(getAccount()));

        // when
        Account expect = accountServices.findByCpf(clientCFP);

        // then
        assertEquals(clientCFP, expect.getClientCpf());
        assertTrue(expect.isHasJob());
    }

    @Test
    void itShouldReturAccountNotFoundExceptionWhenFindByClientCPF() {
        // given
        String clientCFP = CPF;

        // when
        // then
        assertThrows(EntityNotFoundException.class, () -> accountServices.findByCpf(clientCFP));
    }

    public Account getAccount() {
        return Account.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .clientName("Quitumba Ferreira")
                .clientCpf(CPF)
                .typeAccount(TypeAccount.CA)
                .clientBirth(LocalDate.now())
                .hasJob(true)
                .ownHouse(false)
                .score(Score.GOOD)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public AccountInputDto getAccountInputDto() {
        AccountInputDto inputDto = new AccountInputDto();

        inputDto.setClientName("Quitumba Ferreira");
        inputDto.setTypeAccount("CA");
        inputDto.setHasJob(true);
        inputDto.setOwnHouse(false);
        inputDto.setClientCPF(CPF);
        inputDto.setAddress(getAddressInputDto());

        return inputDto;
    }

    public AccountDto getAccountDto() {
        AccountDto accountDto = new AccountDto();

        accountDto.setClientName("Quitumba Ferreira");
        accountDto.setTypeAccount(TypeAccount.CA);
        accountDto.setHasJob(true);
        accountDto.setOwnHouse(false);
        accountDto.setClientCPF(CPF);

        return accountDto;
    }

    private AddressInputDto getAddressInputDto() {
        AddressInputDto addressInput = new AddressInputDto();

        addressInput.setCep(CEP);
        return addressInput;
    }

    private AddressDto getAddressDto() {
        return new AddressDto();
    }
}