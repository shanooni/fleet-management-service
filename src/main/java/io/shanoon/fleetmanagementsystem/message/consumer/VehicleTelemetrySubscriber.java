package io.shanoon.fleetmanagementsystem.message.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VehicleTelemetrySubscriber {

    private static final Logger log =  LoggerFactory.getLogger(VehicleTelemetrySubscriber.class);

    public void handleMessage(String message, String channel){
        log.info("Received message from channel {} and message {}", channel, message);
        // TODO: add persistence to database
    }

    public void receiveMessage(String message){
        log.info("Receive message: {}", message);
    }
}
