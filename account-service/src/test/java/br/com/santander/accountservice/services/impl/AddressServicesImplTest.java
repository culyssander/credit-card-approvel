package br.com.santander.accountservice.services.impl;

import br.com.santander.accountservice.clients.AddressServiceClient;
import br.com.santander.accountservice.models.dto.AddressDto;
import br.com.santander.accountservice.models.dto.AddressInputDto;
import br.com.santander.accountservice.models.dto.AddressResponseClient;
import br.com.santander.accountservice.models.entities.Address;
import br.com.santander.accountservice.repository.AddressRepository;
import br.com.santander.accountservice.services.AddressServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddressServicesImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressServiceClient addressServiceClient;

    private AddressServices addressServices;
    private final String CEP = "01001-000";

    @BeforeEach
    void setup() {
        addressServices = new AddressServicesImpl(addressRepository, addressServiceClient);
    }

    @Test
    void itShoudSaveAddressWithSuccess() {
        // given
        AddressInputDto  inputDto = getAddressInputDto();

        // when
        Mockito.when(addressServiceClient.findAddressByCep(Mockito.anyString())).thenReturn(getAddressResponseClient());
        addressServices.save(inputDto);

        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
        Mockito.verify(addressRepository).save(captor.capture());

        Address addressCapture = captor.getValue();

        // then
        assertEquals(inputDto.getCep(), addressCapture.getCep());
    }

    private AddressInputDto getAddressInputDto() {
        AddressInputDto addressInput = new AddressInputDto();

        addressInput.setCep(CEP);
        return addressInput;
    }

    public AddressResponseClient getAddressResponseClient() {
        AddressResponseClient client = new AddressResponseClient();
        client.setAddress("Rua dos Student");
        client.setCep(CEP);
        client.setUf("SP");
        client.setNeighborhood("Neighborhood");
        return client;
    }
}