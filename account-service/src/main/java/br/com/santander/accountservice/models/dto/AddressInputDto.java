package br.com.santander.accountservice.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressInputDto {

    private String numbers;
    private String complement;
    private String accountNumber;

    @NotNull
    private String cep;

}
