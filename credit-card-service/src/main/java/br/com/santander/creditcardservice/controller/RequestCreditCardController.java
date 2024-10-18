package br.com.santander.creditcardservice.controller;

import br.com.santander.creditcardservice.models.RequestCreditCard;
import br.com.santander.creditcardservice.models.dto.ResponseCreditCard;
import br.com.santander.creditcardservice.models.logs.EventLog;
import br.com.santander.creditcardservice.models.logs.LoggerType;
import br.com.santander.creditcardservice.services.RequestCreditCardServices;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.santander.creditcardservice.util.Constants.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/credit-cards")
public class RequestCreditCardController {

    @Autowired
    private RequestCreditCardServices requestCreditCardServices;

    @GetMapping("/request")
    public ResponseCreditCard request(@RequestParam String clientCPF, @RequestHeader(required = false) String correlationId)  {
        MDC.put(CORRELACTION_ID, correlationId);
        EventLog.builder()
                .event(EVENT_CREDIT_CARD_REQUEST_START_EXECUTION)
                .message(RequestCreditCardController.class.getMethods()[0].getName())
                .field(KEY_FIELD_CREDIT_CARD_REQUEST, clientCPF)
                .build()
                .printLog(log, LoggerType.INFO);

        ResponseCreditCard responseCreditCard = requestCreditCardServices.requestCard(clientCPF);

        EventLog.builder()
                .event(EVENT_CREDIT_CARD_REQUEST_END_EXECUTION)
                .message(responseCreditCard.toString())
                .field(KEY_FIELD_CREDIT_CARD_REQUEST, clientCPF)
                .build()
                .printLog(log, LoggerType.INFO);

        return responseCreditCard;
    }

    @GetMapping("/histories")
    public List<RequestCreditCard> findAll() {
        return requestCreditCardServices.findAll();
    }

}
