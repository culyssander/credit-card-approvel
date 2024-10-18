package br.com.santander.accountservice.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountInputDto {

    @NotBlank
    private String clientName;

    @NotBlank
    private String clientCPF;

    private LocalDate clientBirth;

    @NotNull
    private boolean hasJob;

    @NotNull
    private boolean ownHouse;

    @NotBlank
    private String typeAccount;

    @NotNull
    private AddressInputDto address;

}
