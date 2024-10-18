package br.com.santander.accountservice.models.dto;

import br.com.santander.accountservice.models.type.TypeAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String accountNumber;
    private String clientName;
    private String clientCPF;
    private String clientBirth;
    private boolean hasJob;
    private boolean ownHouse;
    private LocalDateTime createdAt;
    private TypeAccount typeAccount;
    private AddressDto address;
}
