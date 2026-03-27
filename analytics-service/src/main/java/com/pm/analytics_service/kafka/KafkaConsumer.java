package com.pm.analytics_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.protobuf.InvalidProtocolBufferException;

import patient.events.PatientEvent;


@Service
public class KafkaConsumer {

    final static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId="analytics-service")
    public void consumeEvent(byte[] event) {
        System.out.println("Received event: " + new String(event)); // TODO: Debug only
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            logger.info("Received Patient Event: [PatientId={}, PatientName={}, PatientEmail={} ]", 
                patientEvent.getPatientId(), 
                patientEvent.getName(),
                patientEvent.getEmail());
            // ... perform business logic related to analytics
        } catch (InvalidProtocolBufferException e) {
            logger.error("Error deserializing event {}", e.getMessage() );
        }
    }
}
