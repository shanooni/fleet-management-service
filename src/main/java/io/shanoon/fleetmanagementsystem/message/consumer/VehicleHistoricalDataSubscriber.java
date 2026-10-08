package io.shanoon.fleetmanagementsystem.message.consumer;

import io.shanoon.fleetmanagementsystem.repository.IVehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VehicleHistoricalDataSubscriber {

    private static final Logger log =  LoggerFactory.getLogger(VehicleHistoricalDataSubscriber.class);


    public void handleMessage(String message, String channel){
        log.info("Received message from channel {} and message {}", channel, message);
        // TODO: add persistence to database
    }

    public void receiveMessage(String message){
        log.info("Receive message: {}", message);
    }
}
