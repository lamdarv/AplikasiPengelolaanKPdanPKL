package com.jtk.ps.api.dto;

import lombok.Data;

@Data
public class CreateAccountResponse {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
