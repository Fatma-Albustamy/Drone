package com.example.drone.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class DroneMedicationLoadedDto {

    DroneDto droneDto;
    List<MedicationDto> medicationDtos = new ArrayList<>();
}
