package br.com.santander.accountservice.services.impl;

import br.com.santander.accountservice.clients.AddressServiceClient;
import br.com.santander.accountservice.models.dto.AddressInputDto;
import br.com.santander.accountservice.models.dto.AddressResponseClient;
import br.com.santander.accountservice.models.entities.Address;
import br.com.santander.accountservice.repository.AddressRepository;
import br.com.santander.accountservice.services.AddressServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddressServicesImpl implements AddressServices {

    private AddressRepository addressRepository;
    private AddressServiceClient addressServiceClient;

    public AddressServicesImpl(AddressRepository addressRepository, AddressServiceClient addressServiceClient) {
        this.addressRepository = addressRepository;
        this.addressServiceClient = addressServiceClient;
    }


    @Override
    public Address save(AddressInputDto addressInput) {
        AddressResponseClient addressResponseClient = addressServiceClient.findAddressByCep(addressInput.getCep());
        Address address = getAddress(addressResponseClient, addressInput);
        return addressRepository.save(address);
    }

    private Address getAddress(AddressResponseClient client, AddressInputDto address) {
        return Address.builder()
                .address(String.format("%s %s, %s %s", client.getAddress(), client.getComplement(),
                        client.getNeighborhood(), client.getUf()))
                .cep(client.getCep())
                .numbers(address.getNumbers())
                .complement(address.getComplement())
                .accountNumber(address.getAccountNumber())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
