package br.com.santander.accountservice.repository;

import br.com.santander.accountservice.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByClientCpf(String clientCpf);
    boolean existsByClientCpf(String clientCpf);
}
