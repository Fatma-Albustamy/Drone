package com.example.drone.model;

import com.example.drone.model.enums.ModelEnum;
import com.example.drone.model.enums.StateEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter
@Getter
@Builder
public class DroneDto {

    private Long id;

    @NotBlank(message = "serial Number is mandatory ")
    @Size(max = 100, message = "maximum characters of serial Number are 100")
    private String serialNumber;

    private ModelEnum model;

    @Max(value = 500, message = "Max weight is 500 gm")
    private float weight;

    @Max(value = 100, message = "batteryCapacity max 100 ")
    private Integer batteryCapacity;

    private StateEnum state;

}
