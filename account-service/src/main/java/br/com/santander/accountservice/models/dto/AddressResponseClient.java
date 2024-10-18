package br.com.santander.accountservice.models.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseClient implements Serializable {
    private String cep;
    private String address;
    private String complement;
    private String neighborhood;
    private String location;
    private String uf;
    private String status;
    private String region;
}
