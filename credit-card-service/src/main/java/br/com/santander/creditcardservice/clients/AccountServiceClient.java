package br.com.santander.creditcardservice.clients;

import br.com.santander.creditcardservice.models.dto.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "accountServiceClient", url = "${account.service.url}")
public interface AccountServiceClient {

    @GetMapping("/cpf/{cpf}")
    public Account findByClientCpf(@PathVariable String cpf);
}
