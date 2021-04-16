package br.com.stoom.desafio.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GeoCodingResult {

    @JsonProperty(value = "formatted_address")
    private String formattedAddress;

    @JsonProperty("place_id")
    private String placeId;

    private Geometry geometry;
}
