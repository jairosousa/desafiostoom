package br.com.stoom.desafio.api.service.impl;

import br.com.stoom.desafio.api.model.Address;
import br.com.stoom.desafio.api.repository.AddressRepository;
import br.com.stoom.desafio.api.service.AddressService;
import br.com.stoom.desafio.api.service.GeocodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final GeocodeService geocodeService;

    public AddressServiceImpl(AddressRepository addressRepository, GeocodeService geocodeService) {
        this.addressRepository = addressRepository;
        this.geocodeService = geocodeService;
    }

    @Override
    public Address save(Address address) {

        if (Objects.isNull(address.getLatitude()) || Objects.isNull(address.getLongitude())) {
            address = geocodeService.getGeoConding(address);
        }

        Address addressSave = addressRepository.save(address);
        logger.info("Adress save with sucess");
        return addressSave;
    }

    @Override
    public Address update(Long id, Address address) {
        Address addressUpdate = getById(id);

        if (Objects.isNull(address.getLatitude()) || Objects.isNull(address.getLongitude())) {
            address = geocodeService.getGeoConding(address);
        }

        BeanUtils.copyProperties(address, addressUpdate, "id");

        return addressRepository.save(addressUpdate);
    }

    @Override
    public Address getById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Address address) {
        if (Objects.isNull(address) || Objects.isNull(address.getId())) {
            logger.error("Adress id cante be null");
            throw new IllegalArgumentException("Adress id cante be null");
        }
        addressRepository.delete(address);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

}
