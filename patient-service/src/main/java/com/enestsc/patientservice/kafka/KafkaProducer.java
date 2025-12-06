package com.enestsc.patientservice.kafka;

import com.enestsc.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();
        log.info("2- Sending event to Kafka topic {}", event);
        try {
//            CompletableFuture<SendResult<String, byte[]>> future = kafkaTemplate.send("patients", event.toByteArray());
//            future.whenComplete((result, ex) -> {
//                if (ex == null) {
//                    log.info("✅ SUCCESS! Message sent to topic: {}, partition: {}, offset: {}",
//                            result.getRecordMetadata().topic(),
//                            result.getRecordMetadata().partition(),
//                            result.getRecordMetadata().offset());
//                } else {
//                    log.error("❌ FAILED! Error sending message to Kafka: {}", ex.getMessage(), ex);
//                }
//            });
            kafkaTemplate.send("patients", event.toByteArray());
        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", event);
        }
    }

}
