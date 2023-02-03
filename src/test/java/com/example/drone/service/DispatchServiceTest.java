package com.example.drone.service;

import com.example.drone.entity.Drone;
import com.example.drone.entity.Medication;
import com.example.drone.exception.BusinessException;
import com.example.drone.model.MedicationDroneDto;
import com.example.drone.model.enums.StateEnum;
import com.example.drone.repository.DroneRepo;
import com.example.drone.repository.MedicationRepo;
import com.example.drone.utils.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DispatchServiceTest {

    @InjectMocks
    DispatchService dispatchService;

    @Mock
    private DroneRepo droneRepo;

    @Mock
    MedicationRepo medicationRepo;


    @Test
    public void assignMedicationToDrone_droneNotFound_throwBusinessException() {
        MedicationDroneDto medicationDroneDto = new MedicationDroneDto();
        given(droneRepo.findById(medicationDroneDto.getDroneId())).willReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> dispatchService.assignMedicationToDrone(medicationDroneDto));
        assertEquals(exception.getMessage(), Constants.DRONE_NOT_FOUND);
    }

    @Test
    public void assignMedicationToDrone_MedicationNotFound_throwBusinessException() {
        MedicationDroneDto medicationDroneDto = new MedicationDroneDto();
        Drone drone = new Drone();
        given(droneRepo.findById(medicationDroneDto.getDroneId())).willReturn(Optional.of(drone));
        given(medicationRepo.findById(medicationDroneDto.getMedicationId())).willReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> dispatchService.assignMedicationToDrone(medicationDroneDto));
        assertEquals(exception.getMessage(), Constants.MEDICATION_NOT_FOUND);
    }

    @Test
    public void assignMedicationToDrone_checkDroneState_throwBusinessException() {
        MedicationDroneDto medicationDroneDto = new MedicationDroneDto();
        Drone drone = new Drone();
        drone.setId(1l);
        drone.setState(StateEnum.LOADED.name());
        Medication medication = new Medication();
        medication.setId(1l);
        given(droneRepo.findById(medicationDroneDto.getDroneId())).willReturn(Optional.of(drone));
        given(medicationRepo.findById(medicationDroneDto.getMedicationId())).willReturn(Optional.of(medication));
        BusinessException exception = assertThrows(BusinessException.class, () -> dispatchService.assignMedicationToDrone(medicationDroneDto));
        assertEquals(exception.getMessage(), Constants.DRONE_BUSY);
    }


    @Test
    public void assignMedicationToDrone_checkMedicationWeightAgainstDrone_throwBusinessException() {
        MedicationDroneDto medicationDroneDto = new MedicationDroneDto();
        Drone drone = new Drone();
        drone.setId(1l);
        drone.setWeight(200);
        drone.setState(StateEnum.IDLE.name());
        Medication medication = new Medication();
        medication.setId(1l);
        medication.setWeight(300);
        given(droneRepo.findById(medicationDroneDto.getDroneId())).willReturn(Optional.of(drone));
        given(medicationRepo.findById(medicationDroneDto.getMedicationId())).willReturn(Optional.of(medication));
        BusinessException exception = assertThrows(BusinessException.class, () -> dispatchService.assignMedicationToDrone(medicationDroneDto));
        assertEquals(exception.getMessage(), Constants.MEDICATION_OVER_WIGHT);
    }


    @Test
    public void assignMedicationToDrone_checkBatteryLevel_throwBusinessException() {
        MedicationDroneDto medicationDroneDto = new MedicationDroneDto();
        Drone drone = new Drone();
        drone.setId(1l);
        drone.setBatteryCapacity(20);
        drone.setWeight(500);
        drone.setState(StateEnum.IDLE.name());
        Medication medication = new Medication();
        medication.setId(1l);
        medication.setWeight(300);
        given(droneRepo.findById(medicationDroneDto.getDroneId())).willReturn(Optional.of(drone));
        given(medicationRepo.findById(medicationDroneDto.getMedicationId())).willReturn(Optional.of(medication));
        BusinessException exception = assertThrows(BusinessException.class, () -> dispatchService.assignMedicationToDrone(medicationDroneDto));
        assertEquals(exception.getMessage(), Constants.MINIMUM_BATTERY_LEVEL_Low);
    }

    @Test
    public void assignMedicationToDrone_successScenario_returnSuccessMessage() throws BusinessException {
        MedicationDroneDto medicationDroneDto = new MedicationDroneDto();
        Drone drone = new Drone();
        drone.setId(1l);
        drone.setState(StateEnum.IDLE.name());
        drone.setBatteryCapacity(60);
        drone.setWeight(500);
        Medication medication = new Medication();
        medication.setId(1l);
        medication.setWeight(300);
        Set<Medication> medicationSet = new HashSet<>();
        medicationSet.add(medication);
        drone.setMedicationSet(medicationSet);
        given(droneRepo.findById(medicationDroneDto.getDroneId())).willReturn(Optional.of(drone));
        given(medicationRepo.findById(medicationDroneDto.getMedicationId())).willReturn(Optional.of(medication));
        dispatchService.assignMedicationToDrone(medicationDroneDto);
        verify(droneRepo).save(drone);
        assertEquals(1, drone.getMedicationSet().size());
    }


}
