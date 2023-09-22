package com.jtk.ps.api.integration;

import com.jtk.ps.api.ManagementContentServiceApplication;
import com.jtk.ps.api.dto.FormSubmitTimeResponse;
import com.jtk.ps.api.dto.Response;
import com.jtk.ps.api.dto.ResponseList;
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

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ManagementContentServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(value = ExecutionMode.CONCURRENT)
public class FormSubmitControllerTest extends IntegrationTest{
    @Test
    void getFormSubmitTimeTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<FormSubmitTimeResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/management-content/form-submit-time"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().size() > 0);
    }

    @Test
    void getFormSubmitTimeByIdTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        Integer id = 1;

        ResponseEntity<Response<FormSubmitTimeResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/management-content/form-submit-time/" + id),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

}
