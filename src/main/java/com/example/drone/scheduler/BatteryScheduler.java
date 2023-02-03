package com.example.drone.scheduler;

import com.example.drone.entity.Drone;
import com.example.drone.entity.DroneEventLog;
import com.example.drone.repository.DroneEventLogRepo;
import com.example.drone.repository.DroneRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;


@Component
public class BatteryScheduler {

    private final DroneRepo droneRepo;

    private final DroneEventLogRepo droneEventLogRepo;

    static final Logger log = LoggerFactory.getLogger(BatteryScheduler.class);


    public BatteryScheduler(DroneRepo droneRepo, DroneEventLogRepo droneEventLogRepo) {
        this.droneRepo = droneRepo;
        this.droneEventLogRepo = droneEventLogRepo;

    }

    @Scheduled(cron = "${fixedRate.in.milliseconds}")
    public void checkAndLogBatteryLevel() {
        log.info("Start Log Battery level");
        List<Drone> droneList = droneRepo.findAllByDeletedFalse();
        List<DroneEventLog> droneEventLogs = new ArrayList<>();
        droneList.stream().forEach(drone -> {
            DroneEventLog droneEventLog = new DroneEventLog();
            droneEventLog.setDroneId(drone.getId());
            droneEventLog.setBatteryCapacity(drone.getBatteryCapacity());
            droneEventLog.setSerialNumber(drone.getSerialNumber());
            droneEventLogs.add(droneEventLog);
        });
        droneEventLogRepo.saveAll(droneEventLogs);
    }
}
