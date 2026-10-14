package io.shanoon.fleetmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class FleetManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetManagementSystemApplication.class, args);
    }

}
