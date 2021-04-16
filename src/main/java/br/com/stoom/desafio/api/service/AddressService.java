package br.com.stoom.desafio.api.service;

import br.com.stoom.desafio.api.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Address save(Address address);

    Address getById(Long id);

    void delete(Address address);

    Optional<Address> findById(Long id);

    List<Address> findAll();

    Address update(Long id, Address address);
}
