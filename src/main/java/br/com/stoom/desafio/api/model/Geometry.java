package br.com.stoom.desafio.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {

    @JsonProperty("location")
    private GeocodeLocation geocodeLocation;
}
