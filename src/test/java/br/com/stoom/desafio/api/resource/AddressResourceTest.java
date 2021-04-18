package br.com.stoom.desafio.api.resource;

import br.com.stoom.desafio.api.builders.AddressBuilder;
import br.com.stoom.desafio.api.model.Address;
import br.com.stoom.desafio.api.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AddressResource.class)
@AutoConfigureMockMvc
class AddressResourceTest {

    private static final Long ID = 1L;
    private static final String ENDPOINT = "/adresses";

    @Autowired
    MockMvc mvc;

    @MockBean
    AddressService service;

    AddressBuilder builder;

    @BeforeEach
    void setUp() {
        builder = AddressBuilder.builder();

    }

    @Test
    @DisplayName("Deve criar um Address com sucesso!")
    void save() throws Exception {
        Address addressSave = builder.comId(ID).comCoordenadas().build();

        BDDMockito
                .given(service.save(Mockito.any(Address.class))).willReturn(addressSave);

        String json = new ObjectMapper().writeValueAsString(builder.comCoordenadas().build());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(ID));
    }

    @Test
    @DisplayName("Deve atualizar um Address com sucesso!")
    void update() throws Exception {
        Address addressSave = builder.comId(ID).comCoordenadas().build();

        BDDMockito
                .given(service.update(Mockito.any(Long.class),Mockito.any(Address.class))).willReturn(addressSave);

        String json = new ObjectMapper().writeValueAsString(builder.comCoordenadas().build());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(ENDPOINT.concat("/" + ID))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(ID));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação de um Address")
    public void createInvalidAddresssTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(builder.comCoordenadas().semStreetName().build());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("campos", hasSize(1)));

    }

    @Test
    @DisplayName("Deve obter informações de um Address")
    void findById() throws Exception {
        Address address = builder.comId(ID).comCoordenadas().build();

        BDDMockito.given(service.findById(ID)).willReturn(Optional.of(address));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ENDPOINT.concat("/" + ID))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(address.getId()))
                .andExpect(jsonPath("streetName").value(address.getStreetName()))
                .andExpect(jsonPath("number").value(address.getNumber()))
                .andExpect(jsonPath("complement").value(address.getComplement()))
                .andExpect(jsonPath("neighbourhood").value(address.getNeighbourhood()))
                .andExpect(jsonPath("city").value(address.getCity()))
                .andExpect(jsonPath("state").value(address.getState()))
                .andExpect(jsonPath("country").value(address.getCountry()))
                .andExpect(jsonPath("zipcode").value(address.getZipcode()))
                .andExpect(jsonPath("latitude").value(address.getLatitude()))
                .andExpect(jsonPath("longitude").value(address.getLongitude()));
    }

    @Test
    @DisplayName("Deve retornar resource not found quando o Address não existir")
    public void addressNotFoundException() throws Exception {

        BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(ENDPOINT.concat("/" + ID))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Deve deletar um Address")
    void delete() throws Exception {

        Address address = builder.comId(ID).comCoordenadas().build();

        BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(address));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(ENDPOINT.concat("/" + ID));

        mvc
                .perform(request)
                .andExpect(status().isNoContent());
    }
}