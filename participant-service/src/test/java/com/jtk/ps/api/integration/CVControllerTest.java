package com.jtk.ps.api.integration;

import com.jtk.ps.api.ParticipantServiceApplication;
import com.jtk.ps.api.dto.ListIdRequest;
import com.jtk.ps.api.dto.ParticipantResponse;
import com.jtk.ps.api.dto.Response;
import com.jtk.ps.api.dto.ResponseList;
import com.jtk.ps.api.dto.cv.CVCommitteeResponse;
import com.jtk.ps.api.dto.cv.CVCompanyResponse;
import com.jtk.ps.api.dto.cv.CVGetResponse;
import com.jtk.ps.api.dto.cv.CVParticipantResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ParticipantServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(value = ExecutionMode.CONCURRENT)
class CVControllerTest extends IntegrationTest {
    @Test
    void getCVDetailTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        int idCv = 1;

        ResponseEntity<Response<CVGetResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/participant/cv/detail/"+ idCv),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
}
