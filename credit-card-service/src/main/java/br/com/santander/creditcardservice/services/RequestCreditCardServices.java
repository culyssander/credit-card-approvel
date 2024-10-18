package br.com.santander.creditcardservice.services;

import br.com.santander.creditcardservice.models.RequestCreditCard;
import br.com.santander.creditcardservice.models.dto.ResponseCreditCard;

import java.util.List;

public interface RequestCreditCardServices {

    ResponseCreditCard requestCard(String clientCPF);

    List<RequestCreditCard> findAll();
}
