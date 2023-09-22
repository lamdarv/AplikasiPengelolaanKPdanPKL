package com.jtk.ps.api.unit;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.Account;
import com.jtk.ps.api.model.EProdi;
import com.jtk.ps.api.model.ERole;
import com.jtk.ps.api.model.Lecturer;
import com.jtk.ps.api.repository.AccountRepository;
import com.jtk.ps.api.repository.LecturerRepository;
import com.jtk.ps.api.service.AccountService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.CookieUtil;
import com.jtk.ps.api.util.JwtUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(classes = {AccountServiceTest.class})
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JwtUtil jwtTokenUtil;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private CookieUtil cookieUtil;

    @InjectMocks
    private AccountService accountService;

    @Test
    void readAccountsTest() {
        String token = "token";
        String cookie = "cookie";

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);


        List<Participant> participantList = new ArrayList<>();
        participantList.add( new Participant(1, 1, 1, "Heldi", 191511015, 0, 4.00f, true, 2022));
        participantList.add( new Participant(2, 2, 2, "Ari", 191511005, 0, 4.00f, true, 2022));
        participantList.add( new Participant(3, 3, 3, "Bryan", 191511008, 0, 4.00f, false, 2022));
        ResponseList<Participant> responseList = new ResponseList<>(participantList, HttpStatus.OK.value(), "Success");

        Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("/participant/get-all"),
                any(HttpMethod.class),any(), any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(responseList, HttpStatus.OK));

       List<Account> accountList = new ArrayList<>();
       accountList.add( new Account(1, "191511015", "1234", ERole.PARTICIPANT));
       accountList.add( new Account(2, "191511005", "1234", ERole.PARTICIPANT));
       accountList.add( new Account(3, "191511008", "1234", ERole.PARTICIPANT));

       List<Integer> accountIdList = new ArrayList<>();
       accountIdList.add(1);
       accountIdList.add(2);
       accountIdList.add(3);
       Mockito.when(accountRepository.findByIdIn(accountIdList)).thenReturn(accountList);

       Mockito.when(jwtTokenUtil.getRoleFromToken(anyString())).thenReturn(ERole.HEAD_STUDY_PROGRAM.id);

       Mockito.when(jwtTokenUtil.getProdiFromToken(anyString())).thenReturn(EProdi.D3);


       List<AccountResponse> accountResponseList = new ArrayList<>();
       accountResponseList.add( new AccountResponse(4, "Dosen A", "userA", ERole.HEAD_STUDY_PROGRAM, EProdi.D3));
       accountResponseList.add( new AccountResponse(5, "Dosen B", "userB", ERole.COMMITTEE, EProdi.D3));

       Mockito.when(accountRepository.getAllAccountForHeadStudyProgram(EProdi.D3)).thenReturn(accountResponseList);

       List<CompanyResponse> companyResponseList = new ArrayList<>();
       companyResponseList.add( new CompanyResponse(1, 6, "Company A",
               "Email A", "Address A", "Phone A", "CP A",
               "CP Telp A", "CP Email A", "CP Position A", "Website A",
               2000, Boolean.TRUE, new ArrayList<>(), 100, 1));

       ResponseList<CompanyResponse> responseListCompany = new ResponseList<>(companyResponseList, HttpStatus.OK.value(), "Success");
       Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("/company/get-all"),  any(HttpMethod.class),any(), any(ParameterizedTypeReference.class), any(Object[].class)))
               .thenReturn(new ResponseEntity<>(responseListCompany, HttpStatus.OK));

       List<Integer> accountListCompany = new ArrayList<>();
       accountListCompany.add(6);

       List<Account> accountList2 = new ArrayList<>();
       accountList2.add( new Account(6, "companyA", "1234", ERole.COMPANY));
       Mockito.when(accountRepository.findByIdIn(accountListCompany)).thenReturn(accountList2);

       ReadAccountsResponse readAccountsResponse = accountService.readAccounts(token);

       assertEquals(3, readAccountsResponse.getParticipant().size());
       assertEquals(2, readAccountsResponse.getLecturer().size());
       assertEquals(1, readAccountsResponse.getCompany().size());
    }

    @Test
    @Disabled
    void login(){

    }


    @Test
    void logoutTest(){
        HttpHeaders httpHeaders = accountService.logout();
        Mockito.verify((cookieUtil), Mockito.times(1)).deleteAccessTokenCookie();
        Mockito.verify((cookieUtil), Mockito.times(1)).deleteRefreshTokenCookie();
    }

    @Test
    @Disabled
    void verify(){

    }

    @Test
    @Disabled
    void refresh(){

    }

    @Test
    void saveAccountTest(){
        String token = "token";
        Mockito.when(accountRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        Mockito.when(jwtTokenUtil.getProdiFromToken(anyString())).thenReturn(EProdi.D3);

        Mockito.when(bCryptPasswordEncoder.encode(anyString())).thenReturn("1234");

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account(null, "panitiad3", "1234", ERole.COMMITTEE));

        RegisterRequest registerRequest = new RegisterRequest("panitiad3","1234", ERole.COMMITTEE.id, "Dosen A");
        Account acc = accountService.saveAccount(registerRequest, token);


        Mockito.verify(accountRepository, Mockito.times(1)).findByUsername(registerRequest.getUsername());
        Mockito.verify(lecturerRepository, Mockito.times(1)).save(new Lecturer(0, registerRequest.getName(), EProdi.D3, acc));
        assertEquals("panitiad3", acc.getUsername());
        assertEquals("1234", acc.getPassword());
        assertEquals(ERole.COMMITTEE, acc.getRole());
    }

    @Test
    void findAccountByUsernameTest(){
        String username = "panitiad3";
        Mockito.when(accountRepository.findByUsername(anyString())).thenReturn(Optional.of(new Account(1, username, "1234", ERole.COMMITTEE)));
        Account account = accountService.findAccountByUsername(username);

        assertEquals("panitiad3", account.getUsername());
        assertEquals("1234", account.getPassword());
        assertEquals(ERole.COMMITTEE, account.getRole());

        Mockito.verify(accountRepository, Mockito.times(1)).findByUsername(username);
    }

    @Test
    void findAccountByIdTest(){
        Integer id = 1;
        Mockito.when(accountRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Account(id, "panitiad3", "1234", ERole.COMMITTEE)));
        Account account = accountService.findAccountById(id);

        assertEquals(account.getId(), id);
        assertEquals("panitiad3", account.getUsername());
        assertEquals("1234", account.getPassword());
        assertEquals(ERole.COMMITTEE, account.getRole());

        Mockito.verify(accountRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void checkIfValidOldPasswordTest(){
        Account account = new Account(1, "panitiad3", "1234", ERole.COMMITTEE);
        String oldPassword = "1234";

        Mockito.when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        Boolean valid = accountService.checkIfValidOldPassword(account, oldPassword);
        assertTrue(valid);

        Mockito.verify(bCryptPasswordEncoder, Mockito.times(1)).matches(anyString(), anyString());
    }

    @Test
    void updatePasswordTest(){
        Account account = new Account(1, "panitiad3", "1234", ERole.COMMITTEE);
        String newPassword = "123456";

        accountService.updatePassword(account, newPassword);

        Mockito.verify(accountRepository, Mockito.times(1)).save(new Account(1, "panitiad3", newPassword, ERole.COMMITTEE));
    }

    @Test
    public void updateAccountTest(){
        int id = 1;
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest("1", "Dosen B", ERole.COMMITTEE.id);

        Mockito.when(accountRepository.findById(id)).thenReturn(Optional.of(new Account(1, "panitiad3", "1234", ERole.COMMITTEE)));
        Mockito.when(lecturerRepository.findByAccountId(any(Integer.class))).thenReturn(Optional.of(new Lecturer(1, "Dosen A", EProdi.D3, new Account(1, "panitiad3", "1234", ERole.COMMITTEE))));

        accountService.updateAccount(updateAccountRequest);

        Mockito.verify(lecturerRepository, Mockito.times(1)).save(new Lecturer(1, "Dosen B", EProdi.D3, new Account(1, "panitiad3", "1234", ERole.COMMITTEE)));
        Mockito.verify(accountRepository, Mockito.times(1)).save(new Account(1, "panitiad3", "1234", ERole.COMMITTEE));
    }

    @Test
    public void deleteAccountTest(){
        Account account = new Account(1, "panitiad3", "1234", ERole.COMMITTEE);
        String cookie = "cookie";

        Lecturer lecturer = new Lecturer(1, "Dosen A", EProdi.D3, account);
        Mockito.when(lecturerRepository.findByAccountId(any(Integer.class))).thenReturn(Optional.of(lecturer));

        CompanyResponse companyResponse = new CompanyResponse(1, 6, "Company A",
                "Email A", "Address A", "Phone A", "CP A",
                "CP Telp A", "CP Email A", "CP Position A", "Website A",
                2000, Boolean.TRUE, new ArrayList<>(), 100, 1);

        Mockito.when(restTemplate.exchange(ArgumentMatchers.contains("/company/update-pic/"),
                        any(HttpMethod.class),any(), any(Class.class))).thenReturn(new ResponseEntity<>(companyResponse, HttpStatus.OK));

        accountService.deleteAccount(account, cookie);

        Mockito.verify(lecturerRepository, Mockito.times(1)).delete(new Lecturer(1, "Dosen A", EProdi.D3, account));
        Mockito.verify(accountRepository, Mockito.times(1)).delete(account);
    }

    @Test
    public void loadUserByUsernameTest(){
        String username = "panitiad3";

        Mockito.when(accountRepository.findByUsername(username)).thenReturn(Optional.of(new Account(1, "panitiad3", "1234", ERole.COMMITTEE)));

        UserDetails userDetails = accountService.loadUserByUsername(username);

        assertEquals(username,userDetails.getUsername());
        assertEquals("1234", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals(ERole.COMMITTEE.toString(),userDetails.getAuthorities().iterator().next().getAuthority(), userDetails.getAuthorities().iterator().next().getAuthority());
    }



}
