package br.com.stoom.desafio.api.builders;

import br.com.stoom.desafio.api.model.Address;

public class AddressBuilder {

    private Address address;

    public static AddressBuilder builder() {
        AddressBuilder builder = new AddressBuilder();
        builder.address = new Address();
        builder.address.setStreetName("Julia Betina");
        builder.address.setNumber("670");
        builder.address.setComplement("Centro");
        builder.address.setNeighbourhood("Laranjeiras");
        builder.address.setZipcode("38410278");
        builder.address.setCity("Uberlândia");
        builder.address.setState("Minas Gerais");
        builder.address.setCountry("Brasil");

        return builder;
    }

    public AddressBuilder comId(Long id) {
        address.setId(id);
        return this;
    }

    public AddressBuilder comCoordenadas() {
        address.setLatitude("-1.536698");
        address.setLongitude("-48.025634");
        return this;
    }

    public AddressBuilder semStreetName() {
        address.setStreetName(null);
        return this;
    }

    public Address build() {
        return address;
    }
}
