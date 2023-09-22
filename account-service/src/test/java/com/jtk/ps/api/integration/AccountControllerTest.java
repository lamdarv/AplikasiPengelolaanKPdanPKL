package com.jtk.ps.api.integration;


import com.jtk.ps.api.AccountServiceApplication;
import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.ERole;
import com.jtk.ps.api.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(value = ExecutionMode.CONCURRENT)
public class AccountControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private final HttpHeaders headers = new HttpHeaders();

    private static String accessToken;

    private String path = "http://" + getIPProperties();

    @Test
    void getAccountsTest(){
        String accessToken = getAccessToken(ERole.COMMITTEE.toString());
        Mockito.when(request.getHeader(Constant.PayloadResponseConstant.COOKIE)).thenReturn("accessToken="+accessToken);

        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<ReadAccountsResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/account/get-all"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        System.out.println(responseEntity);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getData().getCompany().size() > 0);
        assertTrue(responseEntity.getBody().getData().getParticipant().size() > 0);
    }

    @Test
    void verifyTest(){
        String accessToken = getAccessToken(ERole.COMMITTEE.toString());
        Mockito.when(request.getHeader(Constant.PayloadResponseConstant.COOKIE)).thenReturn("accessToken="+accessToken);

        headers.add("COOKIE", accessToken);

        ResponseEntity<Response<Map<String, Object>>> responseEntity = restTemplate.exchange(
                createURLWithPort("/account/verify"),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).getData().size() > 0);
    }

    @Test
    void getCommitteeByIdTest(){
        String accessToken = getAccessToken(ERole.COMMITTEE.toString());
        Mockito.when(request.getHeader(Constant.PayloadResponseConstant.COOKIE)).thenReturn("accessToken="+accessToken);

        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<CommitteeResponse>> responseEntity = restTemplate.exchange(
                createURLWithPort("/account/get-committee"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    private String createURLWithPort(String uri) {
        return path + ":8080" + uri;
    }

    private String getAccessToken(String role){
        String url = path + ":8080/account/login";
        String username;
        String password;

        MockHttpServletRequest req = new MockHttpServletRequest();

        if(Objects.equals(role, ERole.COMMITTEE.toString())){
            username = "panitiad3";
            password = "1234";
        }else if(Objects.equals(role, ERole.PARTICIPANT.toString())) {
            username = "191511015";
            password = "1234";
        }else if(Objects.equals(role, ERole.COMPANY.toString())) {
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
