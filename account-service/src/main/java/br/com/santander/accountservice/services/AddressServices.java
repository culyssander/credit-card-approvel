package br.com.santander.accountservice.services;

import br.com.santander.accountservice.models.dto.AddressInputDto;
import br.com.santander.accountservice.models.entities.Address;

public interface AddressServices {

    Address save(AddressInputDto address);
}
