package br.com.santander.accountservice.clients;

import br.com.santander.accountservice.models.dto.AddressResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "addressServiceClient", url = "${address.service.url}")
public interface AddressServiceClient {

    @GetMapping(path = "/{cep}")
    AddressResponseClient findAddressByCep(@PathVariable String cep);
}
