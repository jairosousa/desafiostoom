package br.com.stoom.desafio.api.repository;

import br.com.stoom.desafio.api.builders.AddressBuilder;
import br.com.stoom.desafio.api.model.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AddressRepositoryTest {

    private static final Long ID = 1L;
    private static final String NEW_STREET = "Rua nova";
    private static final String NEW_NUMBER = "123";
    private static final String NEW_ZIPCODE = "66825-532";

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AddressRepository repository;

    AddressBuilder builder;

    @BeforeEach
    void setUp() {
        builder = AddressBuilder.builder();

    }

    @Test
    @DisplayName("Deve salvar um Address")
    void saveAddress() {

        Address address = builder.comCoordenadas().build();

        Address save = repository.save(address);

        assertNotNull(save.getId());
        assertNotNull(save.getLongitude());
        assertNotNull(save.getLatitude());
    }

    @Test
    @DisplayName("Deve atualizar um Address")
    void updateAddress() {

        Address address = builder.comCoordenadas().build();

        Address persist = entityManager.persist(address);
        persist.setStreetName(NEW_STREET);
        persist.setNumber(NEW_NUMBER);
        persist.setZipcode(NEW_ZIPCODE);

        Address save = repository.save(persist);

        assertNotNull(save.getId());
        assertEquals(save.getId(), persist.getId());
        assertEquals(save.getStreetName(), NEW_STREET);
    }

    @Test
    @DisplayName("Deve deletar um Address")
    void deleteAddress() {

        Address address = builder.comCoordenadas().build();

        Address persist = entityManager.persist(address);

        repository.delete(persist);

        Optional<Address> optionalAddress = repository.findById(persist.getId());

        assertFalse(optionalAddress.isPresent());
    }

    @Test
    @DisplayName("Deve obter um Address")
    void findByIdAddress() {

        Address address = builder.comCoordenadas().build();

        Address persist = entityManager.persist(address);

        Optional<Address> optionalAddress = repository.findById(persist.getId());

        assertTrue(optionalAddress.isPresent());
    }
}