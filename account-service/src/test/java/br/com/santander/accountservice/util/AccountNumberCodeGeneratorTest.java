package br.com.santander.accountservice.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AccountNumberCodeGeneratorTest {

    private AccountNumberCodeGenerator accountNumberCodeGenerator;

    @BeforeEach
    void setup() {
        accountNumberCodeGenerator = new AccountNumberCodeGenerator();
    }

    @Test
    void generate() {
        // when
        String code = accountNumberCodeGenerator.generate();

        // then
        assertNotEquals(null, code);
        assertEquals(8, code.length());
    }
}