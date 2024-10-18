package br.com.santander.accountservice.models.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeAccount {

    CA("CURRENT ACCOUNT"),
    SA("SAVINGS ACCOUNT");

    private String type;
}
