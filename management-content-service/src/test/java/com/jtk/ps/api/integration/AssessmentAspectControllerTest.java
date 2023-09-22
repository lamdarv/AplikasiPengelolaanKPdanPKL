package com.jtk.ps.api.integration;

import com.jtk.ps.api.ManagementContentServiceApplication;
import com.jtk.ps.api.dto.ResponseList;
import com.jtk.ps.api.model.AssessmentAspect;
import org.junit.jupiter.api.Disabled;
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
class AssessmentAspectControllerTest extends IntegrationTest{
    @Test
    void getAssessmentAspectFormTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        int idProdi = 0;
        int numeval = 1;

        ResponseEntity<ResponseList<String>> responseEntity = restTemplate.exchange(
                createURLWithPort("/management-content/assessment-aspect/get-form?prodi="+idProdi+"&numeval="+numeval),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getData());
        assertTrue(responseEntity.getBody().getData().size() > 0);
    }

    @Test
    void getAssessmentAspectTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<AssessmentAspect>> responseEntity = restTemplate.exchange(
                createURLWithPort("/management-content/assessment-aspect"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getData());
        assertTrue(responseEntity.getBody().getData().size() > 0);
    }

}
