package br.com.santander.accountservice.models.entities;

import br.com.santander.accountservice.models.type.Score;
import br.com.santander.accountservice.models.type.TypeAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "accountNumber")
@ToString
@Entity
@Table(name = "account")
public class Account {
    @Id
    private String accountNumber;
    private String clientName;
    private String clientCpf;
    private LocalDate clientBirth;
    private boolean hasJob;
    private boolean ownHouse;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;

    @Enumerated(EnumType.STRING)
    private Score score;
}
