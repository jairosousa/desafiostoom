package br.com.stoom.desafio.api.resource;

import br.com.stoom.desafio.api.model.Address;
import br.com.stoom.desafio.api.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("adresses")
@RequiredArgsConstructor
public class AddressResource {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> save(@Valid @RequestBody Address address) {
        address = addressService.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @Valid @RequestBody Address address) {

        address = addressService.update(id, address);

        return ResponseEntity.status(HttpStatus.OK).body(address);

    }

    @GetMapping("{id}")
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        Optional<Address> adress = addressService.findById(id);
        if (adress.isPresent()) {
            return ResponseEntity.ok(adress.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Address> optional = addressService.findById(id);
        if (optional.isPresent()) {
            addressService.delete(optional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
