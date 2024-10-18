package br.com.santander.accountservice.controller;

import br.com.santander.accountservice.exception.EntityNotFoundException;
import br.com.santander.accountservice.models.dto.AccountDto;
import br.com.santander.accountservice.models.dto.AccountInputDto;
import br.com.santander.accountservice.models.dto.AddressInputDto;
import br.com.santander.accountservice.models.entities.Account;
import br.com.santander.accountservice.models.type.Score;
import br.com.santander.accountservice.models.type.TypeAccount;
import br.com.santander.accountservice.services.AccountServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String CPF = "04688560052";

    @MockBean
    private AccountServices accountServices;

    @Test
    void itShouldCreateAccount() throws Exception {
        // given
        AccountInputDto inputDto = getAccountInputDto();
        ObjectMapper mapper = new ObjectMapper();

        // when
        Mockito.when(accountServices.save(inputDto)).thenReturn(getAccountDto());

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts/create")
                .content(mapper.writeValueAsString(inputDto)).contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"accountNumber\":null,\"clientName\":\"Quitumba Ferreira\",\"clientCPF\":\"04688560052\",\"clientBirth\":null,\"hasJob\":true,\"ownHouse\":false,\"createdAt\":null,\"typeAccount\":\"CA\",\"address\":null}"));

    }

    @Test
    void itShouldReturnBadRequestWhenInvalidInput() throws Exception {
        // given
        AccountInputDto inputDto = new AccountInputDto();
        ObjectMapper mapper = new ObjectMapper();

        // when
        Mockito.when(accountServices.save(inputDto)).thenReturn(getAccountDto());

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts/create")
                        .content(mapper.writeValueAsString(inputDto)).contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void itShouldReturnNotFoundWhenInvalidURL() throws Exception {
        // given
        AccountInputDto inputDto = getAccountInputDto();
        ObjectMapper mapper = new ObjectMapper();

        // when
        Mockito.when(accountServices.save(inputDto)).thenReturn(getAccountDto());

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .content(mapper.writeValueAsString(inputDto)).contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json("{\"type\":\"about:blank\",\"title\":\"Not Found\",\"status\":404,\"detail\":\"No static resource .\",\"instance\":\"/\"}"));

    }

    @Test
    void itShouldReturnStatusOKWhenFindByCPF() throws Exception {
        // given
        String CPF = "04688560052";
        Mockito.when(accountServices.findByCpf(CPF)).thenReturn(getAccount());

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/cpf/" + CPF))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldReturnThrowWhenFindByCPF() throws Exception {
        // given
        String CPF = "04688560052";
        Mockito.when(accountServices.findByCpf(CPF)).thenThrow(EntityNotFoundException.class);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/cpf/" + CPF))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    public AccountInputDto getAccountInputDto() {
        AccountInputDto inputDto = new AccountInputDto();

        inputDto.setClientName("Quitumba Ferreira");
        inputDto.setTypeAccount("CA");
        inputDto.setHasJob(true);
        inputDto.setOwnHouse(false);
        inputDto.setClientCPF(CPF);
        inputDto.setTypeAccount("CA");
        inputDto.setAddress(getAddressInputDto());

        return inputDto;
    }

    private AddressInputDto getAddressInputDto() {
        AddressInputDto addressInput = new AddressInputDto();

        addressInput.setCep("01001-000");
        return addressInput;
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

    public Account getAccount() {
        return Account.builder()
                .accountNumber("0000000-0")
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
}