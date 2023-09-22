package com.jtk.ps.api.dto;

import com.jtk.ps.api.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyResponse {

    private HttpStatus httpStatus;
    private Map<String, Object> response;
    private String message;
    private HttpHeaders headers;
    private Account account;
}
