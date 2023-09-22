package com.jtk.ps.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@Data
@AllArgsConstructor
public class RefreshResponse {
    private Map<String, Object> response;
    private HttpHeaders headers;
}
