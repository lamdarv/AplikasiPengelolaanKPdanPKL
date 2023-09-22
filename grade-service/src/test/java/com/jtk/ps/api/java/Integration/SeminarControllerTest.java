package com.jtk.ps.api.java.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;

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

import com.jtk.ps.api.GradeServiceApplication;
import com.jtk.ps.api.dto.CompanyDto;
import com.jtk.ps.api.dto.ExaminerSeminarDto;
import com.jtk.ps.api.dto.RecapitulationResponseDto;
import com.jtk.ps.api.dto.Response;
import com.jtk.ps.api.dto.ResponseList;
import com.jtk.ps.api.dto.SeminarCriteriaDto;
import com.jtk.ps.api.dto.SeminarFormResponseDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GradeServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(value = ExecutionMode.CONCURRENT)
public class SeminarControllerTest extends IntegrationTest{
    
    @Test
    void getAllCompanyTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<CompanyDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/seminar/company"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getAllSeminarCriteriaTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<SeminarCriteriaDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/seminar/criteria"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void findAllSeminarFormByParticipantIdTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<SeminarFormResponseDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/seminar/form/participant/191511090"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().getDataForm().size());
    }

    @Test
    void getRecapitulationSeminarByYearAndProdiTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<RecapitulationResponseDto >> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/seminar/recapitulation?year=2023&prodiId=1"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getData());
    }

    @Test
    void getExaminerSeminarTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<ExaminerSeminarDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/seminar/examiner"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getData());
    }
}
