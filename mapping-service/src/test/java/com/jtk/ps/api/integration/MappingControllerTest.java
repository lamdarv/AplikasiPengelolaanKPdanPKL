package com.jtk.ps.api.integration;

import com.jtk.ps.api.MappingServiceApplication;
import com.jtk.ps.api.dto.FinalMappingResponse;
import com.jtk.ps.api.dto.ParticipantFinalMappingResponse;
import com.jtk.ps.api.dto.Response;
import com.jtk.ps.api.dto.ResponseList;
import com.jtk.ps.api.dto.ranking.RankingResponse;
import com.jtk.ps.api.model.CriteriaMapping;
import com.jtk.ps.api.util.Constant;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MappingServiceApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MappingControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final HttpHeaders headers = new HttpHeaders();

    private static String accessToken;

    private String path = "http://" + getIPProperties();


    @Test @Timeout(value = 90 , unit = TimeUnit.SECONDS)
    @Disabled
    void generateRankTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<Boolean>> responseEntity = restTemplate.exchange(
                createURLWithPort("/mapping/generate-rank"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getParticipantInFinalMappingTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        Integer idCompany = 1;

        ResponseEntity<ResponseList<ParticipantFinalMappingResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/mapping/final/company/"+idCompany),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().size() > 0);
    }

    @Test
    void getFinalMappingTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<FinalMappingResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/mapping/final"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getParticipantRankingTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<RankingResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/mapping/get-rank"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().size() > 0);
    }

    @Test
    void getCriteriaTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<CriteriaMapping>> responseEntity = restTemplate.exchange(
                createURLWithPort("/mapping/criteria"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().size() > 0);
    }

    @Test
    @Disabled
    void exportMappingTest(){}

    @Test
    void getIsFinalMappingTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        Integer id = 1;

        ResponseEntity<Response<Boolean>> responseEntity = restTemplate.exchange(
                createURLWithPort("/mapping/get-is-final/"+id),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    private String createURLWithPort(String uri) {
        return path + ":8080" + uri;
    }

    private String getAccessToken(String role){
        String url = path + ":8080/account/login";
        String username;
        String password;

        if(Objects.equals(role, Constant.Role.COMMITTEE)){
            username = "panitiad4";
            password = "1234";
        }else if(Objects.equals(role, Constant.Role.PARTICIPANT)) {
            username = "191511015";
            password = "1234";
        }else if(Objects.equals(role, Constant.Role.COMPANY)) {
            username = "kabayan";
            password = "1234";
        }else{
            username = "kaprodid3";
            password = "admin";
        }

        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return String.valueOf(response.getHeaders().get("Set-Cookie").get(0));
    }

    private String getIPProperties() {
        Properties prop = new Properties();
        String propFileName = "application.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        try {
            if (inputStream != null) {
                prop.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String ip = prop.getProperty("ip");
        System.out.println(ip);
        return ip;
    }
}
