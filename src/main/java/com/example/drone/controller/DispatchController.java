package com.example.drone.controller;

import com.example.drone.exception.BusinessException;
import com.example.drone.model.DroneDto;
import com.example.drone.model.DroneMedicationLoadedDto;
import com.example.drone.model.MedicationDroneDto;
import com.example.drone.service.DispatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
public class DispatchController {

    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @PostMapping("/registration-drone")
    public ResponseEntity<DroneDto> registerDrone( @RequestBody @Valid DroneDto request) {
        return new ResponseEntity<>(dispatchService.registerDrone(request), HttpStatus.CREATED);
    }

    @GetMapping("/drones/idle")
    public ResponseEntity<List<DroneDto>> registerDrone() {
        return new ResponseEntity<>(dispatchService.retrieveIdleDrones(), HttpStatus.OK);
    }

    @PostMapping("/assign/medication-to-drone")
    public ResponseEntity<MedicationDroneDto> assignMedicationToDrone(@RequestBody MedicationDroneDto medicationDroneDto) throws BusinessException {
        return new ResponseEntity<>(dispatchService.assignMedicationToDrone(medicationDroneDto), HttpStatus.OK);
    }

    @GetMapping("/drone/medications/{droneId}")
    public ResponseEntity<DroneMedicationLoadedDto> loadingDroneWithMedications(@PathVariable("droneId") long droneId) {
        return new ResponseEntity<>(dispatchService.loadingDroneWithMedications(droneId), HttpStatus.OK);
    }

    @GetMapping("/drone/battery-level/{droneId}")
    public ResponseEntity<DroneDto> checkDroneBatteryLevel(@PathVariable("droneId") long droneId) {
        return new ResponseEntity<>(dispatchService.checkDroneBatteryLevel(droneId), HttpStatus.OK);
    }

}
