package br.com.santander.creditcardservice.controller;

import br.com.santander.creditcardservice.models.dto.ResponseCreditCard;
import br.com.santander.creditcardservice.services.RequestCreditCardServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = RequestCreditCardController.class)
class RequestCreditCardControllerTest {

    @MockBean
    private RequestCreditCardServices requestCreditCardServices;
    @Autowired
    private MockMvc mockMvc;

    private final String CPF = "04688560052";

    @Test
    void requestOK() throws Exception {
        // given
        String clientCPF = CPF;
        // when
        Mockito.when(requestCreditCardServices.requestCard(clientCPF)).thenReturn(getResponseCreditCard());

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/credit-cards/request")
                .param("clientCPF", CPF))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"clientName\":\"Quitumba Ferreira\",\"result\":\"APPROVED\",\"createdAt\":\"2024-10-20T02:30:00\"}"));

    }

    @Test
    void requestBadRequest() throws Exception {
        // given
        String clientCPF = "CPF";
        // when
        Mockito.when(requestCreditCardServices.requestCard(clientCPF)).thenThrow(RuntimeException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/credit-cards/request")
                        .param("clientCPF", CPF))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void findAll() throws Exception {
        Mockito.when(requestCreditCardServices.findAll()).thenReturn(new ArrayList<>());

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/credit-cards/histories"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    private ResponseCreditCard getResponseCreditCard() {
        return ResponseCreditCard.builder()
                .clientName("Quitumba Ferreira")
                .result("APPROVED")
                .createdAt(LocalDateTime.of(2024, 10, 20, 02, 30, 00))
                .build();
    }
}