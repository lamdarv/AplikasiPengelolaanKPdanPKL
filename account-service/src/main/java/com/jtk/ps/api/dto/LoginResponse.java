package com.jtk.ps.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Map<String, Object> response;
    private HttpHeaders headers;
}
