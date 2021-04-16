package br.com.stoom.desafio.api.repository;

import br.com.stoom.desafio.api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
