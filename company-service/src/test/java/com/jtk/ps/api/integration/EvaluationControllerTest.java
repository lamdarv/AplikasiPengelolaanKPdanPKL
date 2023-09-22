package com.jtk.ps.api.integration;

import com.jtk.ps.api.CompanyServiceApplication;
import com.jtk.ps.api.dto.EvaluationCardResponse;
import com.jtk.ps.api.dto.EvaluationDetailResponse;
import com.jtk.ps.api.dto.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompanyServiceApplication.class)
@Execution(value = ExecutionMode.CONCURRENT)
class EvaluationControllerTest extends IntegrationTest {

    @Test
    void getEvaluationDetailTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<EvaluationDetailResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/evaluation/1?numeval=1"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody().getData());
    }

    @Test
    void getEvaluationFormTest(){
        accessToken = getAccessToken("COMPANY");
        headers.add("COOKIE", accessToken);

        Integer numeval = 1;
        Integer id = 600;
        Integer prodi = 0;

        ResponseEntity<Response<EvaluationDetailResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/evaluation/form?id="+ id +"8&numeval="+numeval+"&prodi="+prodi),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody().getData());
    }

    @Test
    void getEvaluationDetailByParticipantTest(){
        accessToken = getAccessToken("PARTICIPANT");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<EvaluationDetailResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/evaluation/get-by-participant?numeval=1"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getEvaluationCardByCompanyTest(){
        accessToken = getAccessToken("COMPANY");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<EvaluationCardResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/evaluation/get-by-company"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getEvaluationTableByCommitteeTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<EvaluationCardResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/evaluation/get-by-committee"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Disabled
    void exportPdfTest(){}
}
