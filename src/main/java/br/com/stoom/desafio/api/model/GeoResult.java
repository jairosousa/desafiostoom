package br.com.stoom.desafio.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class GeoResult {

    @JsonProperty(value="status")
    private String status;

    @JsonProperty(value="results")
    private List<GeoCodingResult> geoCodingResults;

}
