package com.jtk.ps.api.integration;

import com.jtk.ps.api.CompanyServiceApplication;
import com.jtk.ps.api.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompanyServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(value = ExecutionMode.CONCURRENT)
class PrerequisiteControllerTest extends IntegrationTest{
    @Test
    void getCardPrerequisiteByCompanyTest(){
        accessToken = getAccessToken("COMPANY");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<PrerequisiteCard>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/prerequisite/card-by-company"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    @Test
    void getCardPrerequisiteByCommitteeTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        Integer companyId = 1;

        ResponseEntity<Response<PrerequisiteCard>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/prerequisite/card-by-committee?id-company="+companyId),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    @Test
    void getPrerequisiteTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        Integer idPrerequisite = 1;

        ResponseEntity<Response<PrerequisiteResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/prerequisite/"+idPrerequisite),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    @Test
    void getPrerequisiteCompanyTest(){
        accessToken = getAccessToken("PARTICIPANT");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<PrerequisiteTableResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/prerequisite/prerequisites-company"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    @Test
    void getQuotaTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<QuotaResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/company/prerequisite/quota"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getData());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().size() > 0);
    }

}
