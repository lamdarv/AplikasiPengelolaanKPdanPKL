package com.jtk.ps.api.integration;

import com.jtk.ps.api.ParticipantServiceApplication;
import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.ParticipantCompany;
import com.jtk.ps.api.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ParticipantServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(value = ExecutionMode.CONCURRENT)
public class CompanySelectionControllerTest extends IntegrationTest{
    @Test
    void getCompanySelectionMappingTest(){
        System.out.println(path);
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<ParticipantCompany>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/company-selection/mapping"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getCompanySelectionTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<CompanySelectionResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/company-selection"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().getCompanySelection().size());
    }

    @Test
    void getCompanySelectionCardTest(){
        accessToken = getAccessToken("PARTICIPANT");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<CompanySelectionCard>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/company-selection/card"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getCompanySelectionDetailTest(){
        accessToken = getAccessToken("PARTICIPANT");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<CompanySelectionDetail>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/company-selection/detail"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

}
