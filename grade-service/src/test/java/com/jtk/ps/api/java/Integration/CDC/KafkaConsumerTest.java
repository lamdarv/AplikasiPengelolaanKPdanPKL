package com.jtk.ps.api.java.Integration.CDC;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.sql.Date;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtk.ps.api.dto.kafka.AccountKafka;
import com.jtk.ps.api.dto.kafka.AssessmentAspectKafka;
import com.jtk.ps.api.dto.kafka.CompanyKafka;
import com.jtk.ps.api.dto.kafka.EvaluationKafka;
import com.jtk.ps.api.dto.kafka.FinalMappingKafka;
import com.jtk.ps.api.dto.kafka.ParticipantKafka;
import com.jtk.ps.api.dto.kafka.SelfAssessmentAspectKafka;
import com.jtk.ps.api.dto.kafka.SelfAssessmentGradeKafka;
import com.jtk.ps.api.dto.kafka.SelfAssessmentKafka;
import com.jtk.ps.api.dto.kafka.SupervisorGradeAspectKafka;
import com.jtk.ps.api.dto.kafka.SupervisorGradeKafka;
import com.jtk.ps.api.dto.kafka.SupervisorGradeResultKafka;
import com.jtk.ps.api.dto.kafka.SupervisorMappingKafka;
import com.jtk.ps.api.dto.kafka.TimelineKafka;
import com.jtk.ps.api.dto.kafka.ValuationKafka;

@SpringBootTest
@EmbeddedKafka
public class KafkaConsumerTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private KafkaConsumer<String, String> kafkaConsumer;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Create Kafka consumer properties
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");

        // Create Kafka consumer
        kafkaConsumer = new KafkaConsumer<>(consumerProps);
    }

    @AfterEach
    void tearDown() {
        // Close the Kafka consumer
        kafkaConsumer.close();
    }

    String sendMessage(String topic, String message, Object eventKafka) throws InterruptedException{
        kafkaTemplate.send(topic, message);
        kafkaTemplate.flush();

        kafkaConsumer.subscribe(Collections.singleton(topic));

        Thread.sleep(2000);

        ConsumerRecords<String, String> receivedRecords = kafkaConsumer.poll(Duration.ofSeconds(1));    
        String receivedMessage = "failed";
        for (ConsumerRecord<String, String> receivedRecord : receivedRecords) {
            receivedMessage = receivedRecord.value();
        }
        return receivedMessage;
    }

    @Test
    void consumeAccountTest() throws InterruptedException {
        String topic = "account_topic";
        AccountKafka accountKafka = new AccountKafka(1, "panitiaD3", 0, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(accountKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send(topic, message);
        kafkaTemplate.flush();

        kafkaConsumer.subscribe(Collections.singleton(topic));

        Thread.sleep(5000);

        ConsumerRecords<String, String> receivedRecords = kafkaConsumer.poll(Duration.ofSeconds(1));    

        for (ConsumerRecord<String, String> receivedRecord : receivedRecords) {
            String receivedMessage = receivedRecord.value();
            assertEquals(message, receivedMessage);

            AccountKafka receivedAccountKafka;
            try {
                receivedAccountKafka = objectMapper.readValue(receivedMessage, AccountKafka.class);
                assertEquals(accountKafka, receivedAccountKafka);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void consumeCompanyTest() throws InterruptedException {
        String topic = "company_topic";
        CompanyKafka eventKafka = new CompanyKafka(null, "eventCompany", "event@gmail.com", 2022, false, 1, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);

        Object receivedEventKafka;
        try {
            receivedEventKafka = objectMapper.readValue(receivedMessage, CompanyKafka.class);
            assertEquals(eventKafka, receivedEventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void consumeEvaluationTest() throws InterruptedException {
        String topic = "evaluation_topic";
        EvaluationKafka eventKafka = new EvaluationKafka(null,"eventComment", 2022, 1, 1, "WHO",1, 44, 181524012, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);

        Object receivedEventKafka;
        try {
            receivedEventKafka = objectMapper.readValue(receivedMessage, EvaluationKafka.class);
            assertEquals(eventKafka, receivedEventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void consumeValuationTest() throws InterruptedException {
        String topic = "valuation_topic";
        ValuationKafka eventKafka = new ValuationKafka(null, "eventAspect", 80, 1, false, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);

        Object receivedEventKafka;
        try {
            receivedEventKafka = objectMapper.readValue(receivedMessage, ValuationKafka.class);
            assertEquals(eventKafka, receivedEventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void consumeAssessmentAspectTest() throws InterruptedException {
        String topic = "assessment_aspect_topic";
        AssessmentAspectKafka eventKafka = new AssessmentAspectKafka(null, "eventAspect", 1, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);

        Object receivedEventKafka;
        try {
            receivedEventKafka = objectMapper.readValue(receivedMessage, AssessmentAspectKafka.class);
            assertEquals(eventKafka, receivedEventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void consumeTimelineTest() throws InterruptedException {
        String topic = "timeline_topic";
        TimelineKafka eventKafka = new TimelineKafka(null, "eventTimeline", Date.valueOf("2021-04-01"), Date.valueOf("2021-04-01"), 1, "topic", "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }

    @Test
    void consumeParticipantTest() throws InterruptedException {
        String topic = "participant_topic";
        ParticipantKafka eventKafka = new ParticipantKafka(null, "Bagas", 2022, false, 1, 1, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }

    @Test
    void consumeFinalMappingTest() throws InterruptedException {
        String topic = "final_mapping_topic";
        FinalMappingKafka eventKafka = new FinalMappingKafka(null, 2022, 1, 1, 1, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }

    @Test
    void consumeSelfAssessmentFormTest() throws InterruptedException {
        String topic = "self_assessment_topic";
        SelfAssessmentKafka eventKafka = new SelfAssessmentKafka(null, Date.valueOf("2022-06-13"), Date.valueOf("2022-06-13"), 191511066, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }

    @Test
    void consumeSelfAssessmentGradeTest() throws InterruptedException {
        String topic = "self_assessment_grade_topic";
        SelfAssessmentGradeKafka eventKafka = new SelfAssessmentGradeKafka(null, 20, 1, 1, 1, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }

    @Test
    void consumeSelfAssessmentAspectTest() throws InterruptedException {
        String topic = "self_assessment_aspect_topic";
        SelfAssessmentAspectKafka eventKafka = new SelfAssessmentAspectKafka(null, "event Aspect", "topic", "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }

    @Test
    void consumeSupervisorGradeTest() throws InterruptedException {
        String topic = "supervisor_topic";
        SupervisorGradeKafka eventKafka = new SupervisorGradeKafka(null, 1, 1, Date.valueOf("2022-06-14"), 1, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }

    @Test
    void consumeSupervisorGradeResultTest() throws InterruptedException {
        String topic = "supervisor_result_topic";
        SupervisorGradeResultKafka eventKafka = new SupervisorGradeResultKafka(null, 1, 1, 1, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }

    @Test
    void consumeSupervisorGradeAspectTest() throws InterruptedException {
        String topic = "supervisor_aspect_topic";
        SupervisorGradeAspectKafka eventKafka = new SupervisorGradeAspectKafka(null, "topic", 0f, "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }

    @Test
    void consumeSupervisorMappingTest() throws InterruptedException {
        String topic = "supervisor_mapping_topic";
        SupervisorMappingKafka eventKafka = new SupervisorMappingKafka(null, 1, 1, 1, 1, Date.valueOf("2022-06-14"), "ADDED");

        String message = null;
        try {
            message = objectMapper.writeValueAsString(eventKafka);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receivedMessage = sendMessage(topic, message, eventKafka);

        assertEquals(message, receivedMessage);
    }
    

}
