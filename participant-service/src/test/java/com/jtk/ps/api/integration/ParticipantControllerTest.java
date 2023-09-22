package com.jtk.ps.api.integration;

import com.jtk.ps.api.ParticipantServiceApplication;
import com.jtk.ps.api.dto.ListIdRequest;
import com.jtk.ps.api.dto.ParticipantResponse;
import com.jtk.ps.api.dto.Response;
import com.jtk.ps.api.dto.ResponseList;
import com.jtk.ps.api.dto.cv.CVCommitteeResponse;
import com.jtk.ps.api.dto.cv.CVCompanyResponse;
import com.jtk.ps.api.dto.cv.CVParticipantResponse;
import com.jtk.ps.api.util.Constant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ParticipantServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(value = ExecutionMode.CONCURRENT)
class ParticipantControllerTest extends IntegrationTest {
    @Test
    void getCVInterestParticipantTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<ParticipantResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/get-all"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getParticipantByIdTest(){
        ListIdRequest listIdRequest = new ListIdRequest();
        listIdRequest.setId(new ArrayList<>());
        listIdRequest.getId().add(191511015);
        listIdRequest.getId().add(1);
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONArray jsonArray = new JSONArray();
        listIdRequest.getId().forEach(jsonArray::put);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ResponseEntity<ResponseList<ParticipantResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/get-by-id"),
                HttpMethod.POST,
                new HttpEntity<>(jsonObject.toString(), headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getParticipantCVTest(){
        accessToken = getAccessToken("PARTICIPANT");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<CVParticipantResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/cv-by-participant"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getParticipantCVByCommitteeTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<CVCommitteeResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/cv-by-committee"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getParticipantCVByCompanyTest(){
        accessToken = getAccessToken("COMPANY");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<CVCompanyResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/cv-by-company"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

}
