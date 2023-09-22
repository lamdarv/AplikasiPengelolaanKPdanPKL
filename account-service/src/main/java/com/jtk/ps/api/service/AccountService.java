package com.jtk.ps.api.service;

import com.jtk.ps.api.controller.AccountController;
import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.*;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.CookieUtil;
import com.jtk.ps.api.util.JwtUtil;
import com.jtk.ps.api.repository.AccountRepository;
import com.jtk.ps.api.repository.LecturerRepository;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AccountService implements UserDetailsService, IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ReadAccountsResponse readAccounts(String token) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ReadAccountsResponse readAccountsResponse = new ReadAccountsResponse();

        HttpHeaders headers = new HttpHeaders();
        String cookie = "accessToken=" + token;
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        HttpEntity<String> req = new HttpEntity<>(headers);

        //Get all account Participant
        ResponseEntity<ResponseList<Participant>> response = restTemplate.exchange("http://participant-service/participant/get-all?year=" + year,
                HttpMethod.GET,req, new ParameterizedTypeReference<>() {});
        List<Participant> participantList = Objects.requireNonNull(response.getBody()).getData();
        List<Integer> accountId = new ArrayList<>();
        for (Participant p : participantList) {
            accountId.add(p.getIdAccount());
        }
        List<Account> accountList = accountRepository.findByIdIn(accountId);
        List<Participant> participantsSorted = participantList.stream().sorted(Comparator.comparing(Participant::getIdAccount)).collect(Collectors.toList());
        List<Account> accountsSorted = accountList.stream().sorted(Comparator.comparing(Account::getId)).collect(Collectors.toList());

        List<AccountResponse> accountResponses = new ArrayList<>();
        for (int i = 0; i < accountId.size(); i++) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setIdAccount(participantsSorted.get(i).getIdAccount());
            accountResponse.setName(participantsSorted.get(i).getName());
            accountResponse.setIdProdi(participantsSorted.get(i).getIdProdi());

            accountsSorted.stream().filter(account -> account.getId() == accountResponse.getIdAccount())
                    .findFirst().ifPresent(account -> {
                    accountResponse.setUsername(account.getUsername());
                    accountResponse.setIdRole(account.getRole().id);
                            });
            accountResponses.add(accountResponse);
        }
        readAccountsResponse.setParticipant(accountResponses);

        // Get all account Lecturer by prodi
        if(jwtTokenUtil.getRoleFromToken(token) == ERole.COMMITTEE.id){
            EProdi prodi = jwtTokenUtil.getProdiFromToken(token);
            readAccountsResponse.setLecturer(accountRepository.getAllAccountForCommittee(prodi));
        }else{
            EProdi prodi = jwtTokenUtil.getProdiFromToken(token);
            readAccountsResponse.setLecturer(accountRepository.getAllAccountForHeadStudyProgram(prodi));
        }


        // Get all account Company
        ResponseEntity<ResponseList<CompanyResponse>> res = restTemplate.exchange("http://company-service/company/get-all",
                HttpMethod.GET, req, new ParameterizedTypeReference<>() {});
        List<CompanyResponse> companyResponses = Objects.requireNonNull(res.getBody()).getData();
        accountId.clear();
        for (CompanyResponse companyResponse : companyResponses) {
            accountId.add(companyResponse.getIdAccount());
        }
        accountList = accountRepository.findByIdIn(accountId);
        accountId.clear();
        accountList.forEach(account -> accountId.add(account.getId()));
        companyResponses.removeIf(companyResponse -> !accountId.contains(companyResponse.getIdAccount()));
        List<CompanyResponse> companyResponseSorted = companyResponses.stream().sorted(Comparator.comparing(CompanyResponse::getIdAccount)).collect(Collectors.toList());
        accountsSorted = accountList.stream().sorted(Comparator.comparing(Account::getId)).collect(Collectors.toList());

        accountResponses = new ArrayList<>();
        for (int i = 0; i < accountsSorted.size(); i++) {
            if(Objects.equals(accountsSorted.get(i).getId(), companyResponseSorted.get(i).getIdAccount())) {
                AccountResponse accountResponse = new AccountResponse();
                accountResponse.setIdAccount(companyResponseSorted.get(i).getIdAccount());
                accountResponse.setName(companyResponseSorted.get(i).getCompanyName());
                accountResponse.setUsername(accountsSorted.get(i).getUsername());
                accountResponse.setIdRole(accountsSorted.get(i).getRole().id);
                accountResponses.add(accountResponse);
            }
        }
        readAccountsResponse.setCompany(accountResponses);

        return readAccountsResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest, String accessToken, String refreshToken) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String username = loginRequest.getUsername();
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("Username or password incorrect"));
        HttpHeaders responseHeaders = new HttpHeaders();
        CustomUserDetails user = new CustomUserDetails(account);

        AtomicReference<Token> newAccessToken = new AtomicReference<>(jwtTokenUtil.generateAccessToken(user));
        AtomicReference<Token> newRefreshToken = new AtomicReference<>(jwtTokenUtil.generateRefreshToken(String.valueOf(user.getId())));

        Map<String, Object> claims = new HashMap<>();
        String cookie = "accessToken=" + newAccessToken.get().getTokenValue() + ";refreshToken=" + newRefreshToken.get().getTokenValue();

        if (account.getRole() == ERole.COMMITTEE || account.getRole() == ERole.HEAD_STUDY_PROGRAM) {
            Optional<Lecturer> lecturer = lecturerRepository.findByAccountId(account.getId());

            lecturer.ifPresent(value -> {
                claims.put(Constant.PayloadResponseConstant.NAME, value.getName());
                claims.put(Constant.PayloadResponseConstant.ID_PRODI, value.getProdi().id);

                newAccessToken.set(jwtTokenUtil.generateAccessToken(user, value.getProdi().id, value.getId(), value.getName()));
                newRefreshToken.set(jwtTokenUtil.generateRefreshToken(String.valueOf(user.getId())));
            });

            if (lecturer.isEmpty()) {
                throw new IllegalStateException("COMMITTEE OR HEAD OF STUDY PROGRAM NOT HAVE DATA NAME");
            }
        } else if (account.getRole() == ERole.PARTICIPANT) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);;

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(account.getId());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Constant.PayloadResponseConstant.ID, jsonArray);
                HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
                ResponseEntity<ResponseList<Participant>> response = restTemplate.exchange("http://participant-service/participant/get-by-account", HttpMethod.POST, request, new ParameterizedTypeReference<>() {
                });
            if (response.getStatusCode().value() == HttpStatus.OK.value()) {
                if(!Objects.requireNonNull(response.getBody()).getData().isEmpty()) {
                    claims.put(Constant.PayloadResponseConstant.NAME, response.getBody().getData().get(0).getName());
                    claims.put(Constant.PayloadResponseConstant.ID_PRODI, response.getBody().getData().get(0).getIdProdi());

                    newAccessToken.set(jwtTokenUtil.generateAccessToken(user, response.getBody().getData().get(0).getIdProdi(), response.getBody().getData().get(0).getIdParticipant(), response.getBody().getData().get(0).getName()));
                    newRefreshToken.set(jwtTokenUtil.generateRefreshToken(String.valueOf(user.getId())));
                }
            }
        } else if(account.getRole() == ERole.COMPANY){
            HttpHeaders headers = new HttpHeaders();
            headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(account.getId());
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(Constant.PayloadResponseConstant.ID_ACCOUNT, jsonArray);
            HttpEntity<String> req = new HttpEntity<>(jsonObject.toString(),headers);
            System.out.println("masuk");
            ResponseEntity<ResponseList<CompanyResponse>> response = restTemplate.exchange("http://company-service/company/get-by-account", HttpMethod.POST, req, new ParameterizedTypeReference<>() {
            });
            if (response.getStatusCode().is3xxRedirection() || response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                throw new IllegalStateException("Error when update pic");
            }else{
                if(!Objects.requireNonNull(response.getBody()).getData().isEmpty()){
                    claims.put(Constant.PayloadResponseConstant.NAME, response.getBody().getData().get(0).getCompanyName());
                    newAccessToken.set(jwtTokenUtil.generateAccessToken(user, null, response.getBody().getData().get(0).getIdCompany(), response.getBody().getData().get(0).getCompanyName()));
                    newRefreshToken.set(jwtTokenUtil.generateRefreshToken(String.valueOf(user.getId())));
                }
            }

        }
        claims.put(Constant.PayloadResponseConstant.USERNAME, account.getUsername());
        claims.put(Constant.PayloadResponseConstant.ID_ROLE, account.getRole().id);

        addAccessTokenCookie(responseHeaders, newAccessToken.get());
        addRefreshTokenCookie(responseHeaders, newRefreshToken.get());

        return new LoginResponse(claims, responseHeaders);
    }

    @Override
    public HttpHeaders logout() {
        HttpHeaders responseHeaders = new HttpHeaders();
        deleteAccessTokenCookie(responseHeaders);
        deleteRefreshTokenCookie(responseHeaders);

        return responseHeaders;
    }

    @Override
    public VerifyResponse verify(String accessToken, String refreshToken) {
        try {
            if (StringUtils.hasText(accessToken) && jwtTokenUtil.validateToken(accessToken)) {
                HashMap<String, Object> payload = jwtTokenUtil.getAllPayloadJwt(accessToken);

                payload.remove(Constant.PayloadResponseConstant.EXPIRED);
                payload.remove(Constant.PayloadResponseConstant.ISSUED_AT);

                return new VerifyResponse(HttpStatus.OK, payload, "Verify successfully", null, null);
            }else{
                RefreshResponse response = refresh(refreshToken);
                return new VerifyResponse(HttpStatus.OK, response.getResponse(), "\"Verify successful.\"", response.getHeaders(), null);
            }
        } catch (ExpiredJwtException ex) {
            boolean refreshTokenValid = jwtTokenUtil.validateToken(refreshToken);
            if (!refreshTokenValid) {
                return new VerifyResponse(HttpStatus.FOUND, null, "Refresh Token is invalid!", null, null);
            }

            RefreshResponse response = refresh(refreshToken);
            return new VerifyResponse(HttpStatus.OK, response.getResponse(), "\"Verify successful.\"", response.getHeaders(), null);
        }
    }

    @Override
    public RefreshResponse refresh(String refreshToken){
        if (StringUtils.hasText(refreshToken) && jwtTokenUtil.validateToken(refreshToken)) {
            String id = jwtTokenUtil.getIdAccountFromToken(refreshToken);
            Account account = accountRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new IllegalStateException("ID not found"));

            HttpHeaders responseHeaders = new HttpHeaders();
            CustomUserDetails user = new CustomUserDetails(account);

            Token newAccessToken = jwtTokenUtil.generateAccessToken(user);
            Token newRefreshToken = jwtTokenUtil.generateRefreshToken(user.getUsername());

            String cookie = "accessToken=" + newAccessToken.getTokenValue() + ";refreshToken=" + newRefreshToken.getTokenValue();

            if(account.getRole() == ERole.COMMITTEE || account.getRole() == ERole.HEAD_STUDY_PROGRAM){
                Optional<Lecturer> lecturer = lecturerRepository.findByAccountId(account.getId());

                if(lecturer.isPresent()){
                    newAccessToken = (jwtTokenUtil.generateAccessToken(user, lecturer.get().getProdi().id, lecturer.get().getId(), lecturer.get().getName()));
                }else{
                    throw new IllegalStateException("COMMITTEE OR HEAD OF STUDY PROGRAM NOT HAVE DATA NAME");
                }
            }else if(account.getRole() == ERole.COMPANY){
                HttpHeaders headers = new HttpHeaders();
                headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
                headers.setContentType(MediaType.APPLICATION_JSON);

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(account.getId());
                JSONObject jsonObject = new JSONObject();

                jsonObject.put(Constant.PayloadResponseConstant.ID_ACCOUNT, jsonArray);
                HttpEntity<String> req = new HttpEntity<>(jsonObject.toString(),headers);
                ResponseEntity<ResponseList<CompanyResponse>> response = restTemplate.exchange("http://company-service/company/get-by-account", HttpMethod.POST, req, new ParameterizedTypeReference<>() {
                });
                if (response.getStatusCode().is3xxRedirection() || response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                    throw new IllegalStateException("Error when update pic");
                }else{
                    if(!Objects.requireNonNull(response.getBody()).getData().isEmpty()){
                        newAccessToken = (jwtTokenUtil.generateAccessToken(user, null, response.getBody().getData().get(0).getIdCompany(), response.getBody().getData().get(0).getCompanyName()));
                    }
                }
            }else if (account.getRole() == ERole.PARTICIPANT){
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);


                JSONArray jsonArray = new JSONArray();
                jsonArray.put(account.getId());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Constant.PayloadResponseConstant.ID_ACCOUNT, jsonArray);

                HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
                ResponseEntity<ResponseList<Participant>> response = restTemplate.exchange("http://participant-service/participant/get-by-account", HttpMethod.POST, request, new ParameterizedTypeReference<>() {
                });
                if (response.getStatusCode().value() == HttpStatus.OK.value()) {
                    if(!Objects.requireNonNull(response.getBody()).getData().isEmpty()) {
                        newAccessToken = (jwtTokenUtil.generateAccessToken(user, response.getBody().getData().get(0).getIdProdi(), response.getBody().getData().get(0).getIdParticipant(), response.getBody().getData().get(0).getName()));
                    }
                }
            }

            HashMap<String, Object> payload = null;
            if(newAccessToken != null) {
                addAccessTokenCookie(responseHeaders, newAccessToken);
                payload = jwtTokenUtil.getAllPayloadJwt(newAccessToken.getTokenValue());
                payload.remove(Constant.PayloadResponseConstant.EXPIRED);
                payload.remove(Constant.PayloadResponseConstant.ISSUED_AT);
            }

            return new RefreshResponse(payload, responseHeaders);
        }

        return new RefreshResponse(null, null);
    }

    @Override
    public Account saveAccount(RegisterRequest registerRequest, String accessToken) {
        Account accountExists = findAccountByUsername(registerRequest.getUsername());
        if (accountExists != null) {
            throw new IllegalStateException("Username already taken");
        }
        Account account = new Account();
        account.setRole(ERole.valueOfId(registerRequest.getIdRole()));
        account.setUsername(registerRequest.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        Account newAccount = accountRepository.save(account);
        try {
            int idProdi = jwtTokenUtil.getProdiFromToken(accessToken).id;
            if(account.getRole() == ERole.COMMITTEE || account.getRole() == ERole.HEAD_STUDY_PROGRAM){
                if (registerRequest.getName() != null) {
                    Lecturer lecturer = new Lecturer();
                    lecturer.setAccount(newAccount);
                    lecturer.setName(registerRequest.getName());
                    lecturer.setProdi(EProdi.valueOfId(idProdi));
                    lecturerRepository.save(lecturer);
                }
            }
        } catch (Exception e) {
            accountRepository.delete(account);
            throw new IllegalStateException("Cookie invalid");
        }
        return newAccount;
    }


    @Override
    public Account findAccountByUsername(String username) {
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.orElse(null);
    }

    @Override
    public Account findAccountById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }

    @Override
    public Boolean checkIfValidOldPassword(Account account, String oldPassword) {
        return bCryptPasswordEncoder.matches(oldPassword, account.getPassword());
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        account.setPassword(bCryptPasswordEncoder.encode(newPassword));
        accountRepository.save(account);
    }

    @Override
    public void updateAccount(UpdateAccountRequest updateAccountRequest) {
        final Optional<Account> account = accountRepository.findById(Integer.parseInt(updateAccountRequest.getIdAccount()));

        account.ifPresent(accountValue -> {
            if (!updateAccountRequest.getName().isEmpty()) {
                Optional<Lecturer> lecturer = lecturerRepository.findByAccountId(accountValue.getId());
                lecturer.ifPresent(lecturerValue -> {
                    lecturerValue.setName(updateAccountRequest.getName());
                    lecturerRepository.save(lecturerValue);
                });
            }
            if (updateAccountRequest.getIdRole() != null) {
                accountValue.setRole(ERole.valueOfId(updateAccountRequest.getIdRole()));
                accountRepository.save(accountValue);
            }
        });
    }

    @Override
    public void deleteAccount(Account account, String cookie) {
        if (account.getRole().id == 0 || account.getRole().id == 3) {
            Optional<Lecturer> lecturer = lecturerRepository.findByAccountId(account.getId());

            if(lecturer.isPresent()){
                HttpHeaders headers = new HttpHeaders();
                headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> req = new HttpEntity<>(headers);

                ResponseEntity<CompanyResponse> response = restTemplate.exchange("http://company-service/company/update-pic/"+lecturer.get().getId(), HttpMethod.PUT, req, CompanyResponse.class);
                if (response.getStatusCode().is3xxRedirection() || response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                    throw new IllegalStateException("Error when update pic");
                }
                lecturerRepository.delete(lecturer.get());
            }
        }
        accountRepository.delete(account);
    }

    @Override
    public List<CommitteeResponse> getCommittee() {
        return accountRepository.fetchCommitteeResponseDataInnerJoin();
    }

    @Override
    public CommitteeResponse getCommittee(Integer id) {
        return accountRepository.fetchCommitteeResponseDataInnerJoin(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findAccountByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("Account not found with username:" + username);
        }
        return new CustomUserDetails(account);
    }

    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }

    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }

    private void deleteAccessTokenCookie(HttpHeaders httpHeaders) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, String.valueOf(cookieUtil.deleteAccessTokenCookie()));
    }

    private void deleteRefreshTokenCookie(HttpHeaders httpHeaders) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, String.valueOf(cookieUtil.deleteRefreshTokenCookie()));
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void deleteParticipantAccountJob() {
        // Participant D3
        LoginRequest lr = new LoginRequest();
        lr.setUsername("panitiad3");
        lr.setPassword("1234");
        LoginResponse loginResponse = login(lr, "", "");
        
        String accessToken = String.valueOf(loginResponse.getHeaders().get("Set-Cookie").get(0));
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constant.PayloadResponseConstant.COOKIE, accessToken);
        HttpEntity<String> req = new HttpEntity<>(headers);
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        
        ResponseEntity<ResponseList<Participant>> response =
                restTemplate.exchange("http://participant-service/participant/get-all?year=" + currentYear,
                HttpMethod.GET,req, new ParameterizedTypeReference<>() {});
        List<Participant> participantList = Objects.requireNonNull(response.getBody()).getData();
        
        for (Participant p: participantList) {
            try {
                accountRepository.deleteById(p.getIdAccount());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        // Participant D4
        lr = new LoginRequest();
        lr.setUsername("panitiad4");
        lr.setPassword("1234");
        loginResponse = login(lr, "", "");
        
        accessToken = String.valueOf(loginResponse.getHeaders().get("Set-Cookie").get(0));
        
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constant.PayloadResponseConstant.COOKIE, accessToken);
        req = new HttpEntity<>(headers);
        
        response = restTemplate.exchange("http://participant-service/participant/get-all?year=" + currentYear,
                HttpMethod.GET,req, new ParameterizedTypeReference<>() {});
        participantList = Objects.requireNonNull(response.getBody()).getData();
        
        for (Participant p: participantList) {
            try {
                accountRepository.deleteById(p.getIdAccount());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
