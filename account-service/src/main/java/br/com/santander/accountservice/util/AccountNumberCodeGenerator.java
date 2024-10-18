package br.com.santander.accountservice.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumberCodeGenerator {

    public String generate() {
        Random generator = new Random();
        String value = String.format("%d-%d", generator.nextInt(999999), generator.nextInt(9));
        return value.length() != 8 ? generate() : value;
    }
}
