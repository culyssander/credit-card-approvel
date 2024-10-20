package br.com.santander.creditcardservice.models.dto;

import br.com.santander.creditcardservice.models.type.Score;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class Account {
    @Id
    private String accountNumber;
    private String clientName;
    private String clientCpf;
    private LocalDate clientBirth;
    private boolean hasJob;
    private boolean ownHouse;
    private LocalDateTime createdAt;
    private String typeAccount;
    private Score score;
}
