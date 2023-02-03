package com.example.drone.service;

import com.example.drone.entity.Drone;
import com.example.drone.entity.Medication;
import com.example.drone.exception.BusinessException;
import com.example.drone.model.DroneDto;
import com.example.drone.model.DroneMedicationLoadedDto;
import com.example.drone.model.MedicationDroneDto;
import com.example.drone.model.MedicationDto;
import com.example.drone.model.enums.ModelEnum;
import com.example.drone.model.enums.StateEnum;
import com.example.drone.repository.DroneRepo;
import com.example.drone.repository.MedicationRepo;
import com.example.drone.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DispatchService {

    static final Logger log = LoggerFactory.getLogger(DispatchService.class);


    private final DroneRepo droneRepo;
    private final MedicationRepo medicationRepo;

    public DispatchService(DroneRepo droneRepo, MedicationRepo medicationRepo) {
        this.droneRepo = droneRepo;
        this.medicationRepo = medicationRepo;
    }

    public DroneDto registerDrone(DroneDto droneDto) {
        Drone drone = droneRepo.save(Drone.builder().serialNumber(droneDto.getSerialNumber())
                .batteryCapacity(droneDto.getBatteryCapacity())
                .model(droneDto.getModel().name()).state(droneDto.getState().name()).weight(droneDto.getWeight()).build());
        droneDto.setId(drone.getId());
        return droneDto;
    }

    public List<DroneDto> retrieveIdleDrones() {
        List<DroneDto> droneDtos = new ArrayList<>();
        List<Drone> drones = droneRepo.findAllByStateEquals(StateEnum.IDLE.name());
        drones.stream().forEach(drone -> {
            droneDtos.add(DroneDto.builder().id(drone.getId()).batteryCapacity(drone.getBatteryCapacity())
                    .model(ModelEnum.valueOf(drone.getModel())).state(StateEnum.valueOf(drone.getState()))
                    .serialNumber(drone.getSerialNumber()).weight(drone.getWeight()).build());
        });
        return droneDtos;
    }

    public MedicationDroneDto assignMedicationToDrone(MedicationDroneDto medicationDroneDto) throws BusinessException {
        Optional<Drone> drone = checkDroneExistence(medicationDroneDto);
        Optional<Medication> medication = checkMedicationExistence(medicationDroneDto, drone);
        checkDroneState(drone.get());
        checkMedicationWeightAgainstDrone(drone.get(), medication.get());
        checkBatteryLevel(drone.get());
        drone.get().getMedicationSet().add(medication.get());
        droneRepo.save(drone.get());
        return medicationDroneDto;

    }

    public DroneMedicationLoadedDto loadingDroneWithMedications(long droneId) {
        DroneMedicationLoadedDto droneMedicationLoadedDto = new DroneMedicationLoadedDto();
        Optional<Drone> drone = droneRepo.findById(droneId);
        if (drone.isPresent()) {
            droneMedicationLoadedDto.setDroneDto(DroneDto.builder().id(drone.get().getId()).
                    model(ModelEnum.valueOf(drone.get().getModel())).batteryCapacity(drone.get().getBatteryCapacity()).state(StateEnum.valueOf(drone.get().getState()))
                    .weight(drone.get().getWeight()).serialNumber(drone.get().getSerialNumber()).build());

            drone.get().getMedicationSet().stream().forEach(medication -> {
                droneMedicationLoadedDto.getMedicationDtos().add(MedicationDto.builder().id(medication.getId()).name(medication.getName()).
                        code(medication.getCode()).image(medication.getImage()).weight(medication.getWeight()).build());
            });
        }
        return droneMedicationLoadedDto;
    }

    public DroneDto checkDroneBatteryLevel(Long droneId) {
        Optional<Drone> drone = droneRepo.findById(droneId);
        return DroneDto.builder().id(drone.get().getId()).
                model(ModelEnum.valueOf(drone.get().getModel())).batteryCapacity(drone.get().getBatteryCapacity()).state(StateEnum.valueOf(drone.get().getState()))
                .weight(drone.get().getWeight()).serialNumber(drone.get().getSerialNumber()).build();
    }

    private void checkMedicationWeightAgainstDrone(Drone drone, Medication medication) throws BusinessException {
        if (drone.getWeight() < medication.getWeight()) {
            throw new BusinessException(Constants.MEDICATION_OVER_WIGHT);
        }
    }

    private void checkBatteryLevel(Drone drone) throws BusinessException {
        if (drone.getBatteryCapacity() < Constants.MINIMUM_BATTERY_LEVEL) {
            throw new BusinessException(Constants.MINIMUM_BATTERY_LEVEL_Low);
        }
    }

    private void checkDroneState(Drone drone) throws BusinessException {
        if (!drone.getState().equals(StateEnum.IDLE.name())) {
            throw new BusinessException(Constants.DRONE_BUSY);
        }
    }

    private Optional<Medication> checkMedicationExistence(MedicationDroneDto medicationDroneDto, Optional<Drone> drone) throws BusinessException {
        Optional<Medication> medication = medicationRepo.findById(medicationDroneDto.getMedicationId());
        if (!medication.isPresent())
            throw new BusinessException(Constants.MEDICATION_NOT_FOUND);
        return medication;
    }

    private Optional<Drone> checkDroneExistence(MedicationDroneDto medicationDroneDto) throws BusinessException {
        Optional<Drone> drone = droneRepo.findById(medicationDroneDto.getDroneId());
        if (!drone.isPresent())
            throw new BusinessException(Constants.DRONE_NOT_FOUND);
        return drone;
    }

}
