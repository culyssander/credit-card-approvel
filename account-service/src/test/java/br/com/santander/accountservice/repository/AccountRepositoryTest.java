package br.com.santander.accountservice.repository;

import br.com.santander.accountservice.models.entities.Account;
import br.com.santander.accountservice.models.type.Score;
import br.com.santander.accountservice.models.type.TypeAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void itShouldCheckIfAccountExistsByClientCPF() {
        // given
        String cpf = "04688560052";
        Account account = getAccount();
        accountRepository.save(account);

        // when
        boolean expected = accountRepository.existsByClientCpf(cpf);

        // then
        assertTrue(expected);
    }

    @Test
    void itShouldCheckIfAccountDoesNotExistsByClientCPF() {
        // given
        String cpf = "04688560052";

        // when
        boolean expected = accountRepository.existsByClientCpf(cpf);

        // then
        assertFalse(expected);
    }

    @Test
    void itShouldReturnAccountWhenFindByClientCPF() {
        // given
        String cpf = "04688560052";
        Account account = getAccount();
        accountRepository.save(account);

        // when
        Optional<Account> expected = accountRepository.findByClientCpf(cpf);

        // then
        assertTrue(expected.isPresent());
    }

    @Test
    void itShouldReturnEmptyWhenFindByClientCPF() {
        // given
        String cpf = "04688560052";

        // when
        Optional<Account> expected = accountRepository.findByClientCpf(cpf);

        // then
        assertTrue(expected.isEmpty());
    }


    public Account getAccount() {
        return Account.builder()
                .accountNumber("046885-9")
                .clientCpf("04688560052")
                .typeAccount(TypeAccount.CA)
                .clientBirth(LocalDate.now())
                .hasJob(true)
                .ownHouse(false)
                .score(Score.GOOD)
                .createdAt(LocalDateTime.now())
                .build();
    }
}