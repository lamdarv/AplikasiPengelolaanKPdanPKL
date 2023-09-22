package com.jtk.ps.api.integration;

import com.jtk.ps.api.ManagementContentServiceApplication;
import com.jtk.ps.api.dto.CompetenceResponse;
import com.jtk.ps.api.dto.Response;
import com.jtk.ps.api.dto.ResponseList;
import com.jtk.ps.api.model.CompetenceType;
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
public class CompetenceControllerTest extends IntegrationTest {
    @Test
    void getTypeCompetenceTest() {
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        Integer id = 1;

        ResponseEntity<Response<Integer>> responseEntity = restTemplate.exchange(
                createURLWithPort("/management-content/competence/get-type?id=" + id),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getData());
        assertInstanceOf(Integer.class, responseEntity.getBody().getData());
    }

    @Test
    void getAllCompetenceTest() {
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<CompetenceResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/management-content/competence/get-all/0"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getData());
        assertTrue(responseEntity.getBody().getData().size() > 0);
    }

    @Test
    void getAllTypeCompetenceTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<CompetenceType>> responseEntity = restTemplate.exchange(
                createURLWithPort("/management-content/competence/get-all-type"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getData());
        assertTrue(responseEntity.getBody().getData().size() > 0);
    }
}
