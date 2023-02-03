package com.example.drone.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Pattern;

@Setter
@Getter
@Builder
public class MedicationDto {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String name;

    private float weight;

    @Pattern(regexp = "^[A-Z0-9_]*$")
    private String code;

    private String image;

}

