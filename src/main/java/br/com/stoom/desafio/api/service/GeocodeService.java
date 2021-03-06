package br.com.stoom.desafio.api.service;

import br.com.stoom.desafio.api.model.Address;
import br.com.stoom.desafio.api.model.GeoCodingResult;
import br.com.stoom.desafio.api.model.GeoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.Normalizer;
import java.util.Optional;


@Service
public class GeocodeService {

    private static final Logger log = LoggerFactory.getLogger(GeocodeService.class);
    private static final String GEOCODING_URI = "https://maps.googleapis.com/maps/api/geocode/json";

    @Value("${api-key}")
    private String apiKey;

    public Address getGeoConding(Address address) {

        RestTemplate restTemplate = new RestTemplate();

        String paramsAddress = getParamsAddress(address);

        log.info("STREET: {}", paramsAddress);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GEOCODING_URI).queryParam("address", paramsAddress)
                .queryParam("key", apiKey);

        log.info("Calling geocoding api with: " + builder.toUriString());

        GeoResult geoCoding = restTemplate.getForObject(builder.toUriString(), GeoResult.class);

        getGeoLocation(address, geoCoding);

        return address;
    }

    private void getGeoLocation(Address address, GeoResult geoCoding) {
        Optional<GeoCodingResult> geoCodingResult = geoCoding.getGeoCodingResults().stream().findFirst();
        address.setLatitude(geoCodingResult.get().getGeometry().getGeocodeLocation().getLatitude());
        address.setLongitude(geoCodingResult.get().getGeometry().getGeocodeLocation().getLongitude());
    }

    private String getParamsAddress(Address address) {

        String streetName = removerAcentos(address.getStreetName().replace(" ", "+"));
        String city = removerAcentos(address.getCity().replace(" ", "+"));
        String state = removerAcentos(address.getState().replace(" ", "+"));
        String neighbourhood = removerAcentos(address.getNeighbourhood().replace(" ", "+"));
        return streetName + "," + address.getNumber() + "," + neighbourhood + "," + city + "," + state;
    }

    private String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
