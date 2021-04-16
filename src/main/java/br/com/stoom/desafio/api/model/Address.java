package br.com.stoom.desafio.api.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "streetName is mandatory")
    @Column
    private String streetName;

    @NotBlank(message = "number is mandatory")
    @Column
    private String number;

    @Column
    private String complement;

    @NotBlank(message = "neighbourhood is mandatory")
    @Column
    private String neighbourhood;

    @NotBlank(message = "city is mandatory")
    @Column
    private String city;

    @NotBlank(message = "state is mandatory")
    @Column
    private String state;

    @NotBlank(message = "country is mandatory")
    @Column
    private String country;

    @NotBlank(message = "zipcode is mandatory")
    @Column
    private String zipcode;

    @Column
    private String latitude;

    @Column
    private String longitude;

}
