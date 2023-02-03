package com.example.drone.utils;

public final class Constants {

    private  Constants(){
        throw new IllegalStateException("Constant class");
    }

    public static final String DRONE_NOT_FOUND = "Drone Not Found";
    public static final String MEDICATION_NOT_FOUND = "Medication Not Found";

    public static final String MEDICATION_OVER_WIGHT = "Medication is over wight";

    public static final Integer MINIMUM_BATTERY_LEVEL = 25;

    public static final String MINIMUM_BATTERY_LEVEL_Low = "Battery level is less than 25%";

    public static final String DRONE_BUSY = "Drone busy now";


}
