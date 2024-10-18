package br.com.santander.creditcardservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "request_card")
public class RequestCreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientCpf;
    private boolean hasJob;
    private boolean ownHouse;
    private String score;
    private String result;
    private String reason;
    private LocalDateTime createdAt;
    private String createdBy;
}
