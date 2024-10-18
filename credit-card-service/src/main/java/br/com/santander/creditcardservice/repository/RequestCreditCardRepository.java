package br.com.santander.creditcardservice.repository;

import br.com.santander.creditcardservice.models.RequestCreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestCreditCardRepository extends JpaRepository<RequestCreditCard, Long> {
}
