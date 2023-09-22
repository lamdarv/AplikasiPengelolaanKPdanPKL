package com.jtk.ps.api.integration;

import com.jtk.ps.api.ManagementContentServiceApplication;
import com.jtk.ps.api.dto.ResponseList;
import com.jtk.ps.api.dto.TimelineRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ManagementContentServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(value = ExecutionMode.CONCURRENT)
class TimelineControllerTest extends IntegrationTest{
    @Test
    void getTimelineTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<TimelineRequest>> responseEntity = restTemplate.exchange(
                createURLWithPort("/management-content/timeline"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getData());
        assertTrue(responseEntity.getBody().getData().size() > 0);
    }
}
