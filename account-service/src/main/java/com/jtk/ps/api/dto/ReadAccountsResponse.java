package com.jtk.ps.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReadAccountsResponse {
    private List<AccountResponse> participant;
    private List<AccountResponse> lecturer = new ArrayList<>();
    private List<AccountResponse> company;
}
