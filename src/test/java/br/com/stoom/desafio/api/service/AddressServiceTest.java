package br.com.stoom.desafio.api.service;

import br.com.stoom.desafio.api.builders.AddressBuilder;
import br.com.stoom.desafio.api.model.Address;
import br.com.stoom.desafio.api.repository.AddressRepository;
import br.com.stoom.desafio.api.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AddressServiceTest {

    private static final Long ID = 1l;

    AddressService addressService;

    @MockBean
    AddressRepository addressRepository;

    @MockBean
    GeocodeService geocodeService;

    AddressBuilder builder;

    @BeforeEach
    public void setup() {
        builder = AddressBuilder.builder();
        this.addressService = new AddressServiceImpl(addressRepository, geocodeService);
    }

    @Test
    @DisplayName("Deve salvar um endereço sem passar coordenadas")
    void save() {

        when(geocodeService.getGeoConding(builder.build())).thenReturn(builder.comCoordenadas().build());

        when(addressRepository.save(builder.comCoordenadas().build()))
                .thenReturn(builder.comId(ID).comCoordenadas().build());

        Address address = addressService.save(builder.comCoordenadas().build());

        assertNotNull(address.getId());
        assertNotNull(address.getLatitude());
        assertNotNull(address.getLongitude());
        assertEquals(address.getStreetName(), builder.build().getStreetName());

    }

    @Test
    @DisplayName("Deve atualizar um endereço completo com coordenadas")
    void saveComplete() {

        when(addressRepository.save(builder.comCoordenadas().build()))
                .thenReturn(builder.comId(ID).comCoordenadas().build());

        Address address = addressService.save(builder.comCoordenadas().build());

        assertNotNull(address.getId());
        assertNotNull(address.getLatitude());
        assertNotNull(address.getLongitude());
        assertEquals(address.getStreetName(), builder.build().getStreetName());
        Mockito.verify(geocodeService, Mockito.never()).getGeoConding(Mockito.any(Address.class));

    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar um Address inválido")
    void notSave() {

        Address addressSave = builder.semStreetName().comCoordenadas().build();

        assertThrows(IllegalArgumentException.class, () -> addressService.delete(addressSave));

        verify(addressRepository, never()).save(addressSave);

    }

    @Test
    @DisplayName("Deve atualizar Address")
    void update() {

        Address addressSave = builder.comCoordenadas().build();
        Address addressUpdate = builder.comId(ID).comCoordenadas().build();
        addressUpdate.setStreetName("Rua Rubiataba");
        addressUpdate.setNumber("998");
        addressUpdate.setNeighbourhood("Bela Vista");

        when(addressRepository.findById(ID)).thenReturn(Optional.of(addressUpdate));

        when(addressRepository.save(addressSave))
                .thenReturn(addressUpdate);

        Address update = addressService.update(ID, addressSave);

        assertEquals(update.getStreetName(), addressUpdate.getStreetName());
        assertEquals(update.getNumber(), addressUpdate.getNumber());
        assertEquals(update.getNeighbourhood(), addressUpdate.getNeighbourhood());

    }

    @Test
    @DisplayName("Deve retornar um address por seu id")
    void findById() {

        Address address = builder.comId(ID).comCoordenadas().build();

        when(addressRepository.findById(ID)).thenReturn(Optional.of(address));

        Optional<Address> optionalAddress = addressService.findById(ID);

        assertTrue(optionalAddress.isPresent());

        assertEquals(optionalAddress.get().getId(), ID);
    }

    @Test
    @DisplayName("Deve deletar Address")
    void delete() {

        Address address = builder.comId(ID).comCoordenadas().build();

        assertDoesNotThrow(() -> addressService.delete(address));

        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    @DisplayName("Deve retornar lista de Addresses")
    void findAll() {
        Address address = builder.comId(ID).comCoordenadas().build();

        when(addressRepository.findAll()).thenReturn(Arrays.asList(address));

        List<Address> addresses = addressService.findAll();

        assertNotNull(address);
        assertEquals(addresses.size(), 1);

    }


}