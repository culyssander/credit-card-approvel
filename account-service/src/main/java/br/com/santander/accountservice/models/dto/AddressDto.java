package br.com.santander.accountservice.models.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class AddressDto {
    private String cep;
    private String address;
    private String numbers;
    private String complement;
}
