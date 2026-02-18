package com.pm.patientservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pm.patientservice.model.Patient;

import patient.events.PatientEvent;

@Service
public class kafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;


    public kafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    final static Logger log = LoggerFactory.getLogger(Patient.class);

    public void SendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED") // or "UPDATED", "DELETED" based on the context
                .build();
        try {
            kafkaTemplate.send("patient", event.toByteArray());
            log.info("PatientCreated event sent: {}", event);
            
        } catch (Exception e) {
            log.error(" Error sending PatientCreated event: {}", event);
        }
    }

}
