package com.jtk.ps.api.service;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.Account;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface IAccountService {
    Account saveAccount(RegisterRequest registerRequest, String accessToken);

    Account findAccountByUsername(String username);

    Account findAccountById(Integer id);

    Boolean checkIfValidOldPassword(Account account, String oldPassword);

    void updatePassword(Account account, String newPassword);

    void updateAccount(UpdateAccountRequest updateAccountRequest);

    ReadAccountsResponse readAccounts(String token);

    LoginResponse login(LoginRequest loginRequest, String accessToken, String refreshToken);

    HttpHeaders logout();

    VerifyResponse verify(String accessToken, String refreshToken);

    void deleteAccount(Account account, String cookie);

    List<CommitteeResponse> getCommittee();

    CommitteeResponse getCommittee(Integer id);

    RefreshResponse refresh(String refreshToken);
}
