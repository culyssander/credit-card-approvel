package br.com.santander.creditcardservice.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCreditCard {
    private String clientName;
    private String result;
    private String reason;
    private LocalDateTime createdAt;
}
