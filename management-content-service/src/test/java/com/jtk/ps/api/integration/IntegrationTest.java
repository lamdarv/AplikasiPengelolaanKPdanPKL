package com.jtk.ps.api.integration;

import com.jtk.ps.api.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class IntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    protected final HttpHeaders headers = new HttpHeaders();

    protected static String accessToken;

    protected String path = "http://" + getIPProperties();



    protected String createURLWithPort(String uri) {
        return path + ":8080" + uri;
    }

    protected String getAccessToken(String role){
        String url = path + ":8080/account/login";
        String username;
        String password;

        MockHttpServletRequest req = new MockHttpServletRequest();

        if(Objects.equals(role, Constant.Role.COMMITTEE)){
            username = "panitiad3";
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

    protected String getIPProperties() {
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
        return ip;
    }
}
