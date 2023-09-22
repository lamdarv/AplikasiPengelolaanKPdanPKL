package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.Account;
import com.jtk.ps.api.service.IAccountService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("")
public class AccountController {
    @Autowired
    private IAccountService service;

    @GetMapping(value = "/get-all")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getAccounts(@ApiParam(hidden = true) @CookieValue(name = "accessToken", required = false) String accessToken, HttpServletRequest request) {
        try {
            String token = (String) request.getAttribute("accessToken");
            if(token == null) {
                token = accessToken;
            }
            ReadAccountsResponse accountResponses = service.readAccounts(token);
            if (accountResponses != null) {
                return ResponseHandler.generateResponse("Get all accounts successfully", HttpStatus.OK, accountResponses);
            }
            return ResponseHandler.generateResponse("Account not found", HttpStatus.OK);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@ApiParam(hidden = true) @CookieValue(name = "accessToken", required = false) String accessToken, @ApiParam(hidden = true) @CookieValue(name = "refreshToken", required = false) String refreshToken, @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = service.login(loginRequest, accessToken, refreshToken);

            return ResponseHandler.generateResponse("Auth successful. Tokens created in cookie.", HttpStatus.OK, loginResponse.getResponse(), loginResponse.getHeaders());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return ResponseHandler.generateResponse("User not authenticated!", HttpStatus.OK);
            }
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            HttpHeaders httpHeaders = service.logout();
            return ResponseHandler.generateResponse("\"Logout successful. Tokens deleted in cookie.\"", HttpStatus.OK, null, httpHeaders);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Object> verify(@CookieValue(name = "accessToken", required = false) String accessToken, @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        try {
            VerifyResponse verifyResponse = service.verify(accessToken, refreshToken);
            if (verifyResponse.getHttpStatus().is3xxRedirection()) {
                return ResponseHandler.generateResponse("Redirect to login!", verifyResponse.getHttpStatus(), verifyResponse.getResponse(), verifyResponse.getHeaders());
            } else {
                return ResponseHandler.generateResponse("Verify successfully!", verifyResponse.getHttpStatus(), verifyResponse.getResponse(), verifyResponse.getHeaders());
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.FOUND);
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> createAccount(@CookieValue(name = "accessToken", required = false) String accessToken, @RequestBody @Valid RegisterRequest registerRequest, HttpServletRequest request) {
        try {
            String token = (String) request.getAttribute("accessToken");
            if(token == null) {
                token = accessToken;
            }
            Account account = service.saveAccount(registerRequest, token);
            return ResponseHandler.generateResponse("Data added successfully!", HttpStatus.OK, account);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> updateDataAccount(@RequestBody UpdateAccountRequest updateAccountRequest) {
        try {
            service.updateAccount(updateAccountRequest);
            return ResponseHandler.generateResponse("Account updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody NewPasswordRequest newPasswordRequest) {
        if (!newPasswordRequest.getNewPassword().equals(newPasswordRequest.getConfirmNewPassword())) {
            return ResponseHandler.generateResponse("New password is not the same as the confirmation of new password", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Account account = service.findAccountByUsername(username);

        if (account == null) {
            return ResponseHandler.generateResponse("No account found with name " + username, HttpStatus.BAD_REQUEST);
        }

        if (Boolean.FALSE.equals(service.checkIfValidOldPassword(account, newPasswordRequest.getOldPassword()))) {
            return ResponseHandler.generateResponse("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        if (authentication != null) {
            service.updatePassword(account, newPasswordRequest.getNewPassword());
        }
        return ResponseHandler.generateResponse("Password updated successfully", HttpStatus.OK);
    }

    @PostMapping("/committee-change-password")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> committeeChangePassword(@RequestBody @Valid CommitteePasswordRequest committeePasswordRequest) {
        if (!committeePasswordRequest.getNewPassword().equals(committeePasswordRequest.getConfirmNewPassword())) {
            return ResponseHandler.generateResponse("New password is not the same as the confirmation of new password", HttpStatus.BAD_REQUEST);
        }

        final Account account = service.findAccountById(committeePasswordRequest.getIdAccount());
        if (account == null) {
            return ResponseHandler.generateResponse("No account found with id " + committeePasswordRequest.getIdAccount(), HttpStatus.BAD_REQUEST);
        }
        try {
            service.updatePassword(account, committeePasswordRequest.getNewPassword());
            return ResponseHandler.generateResponse("Password updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> deleteAccount(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        final Account account = service.findAccountById(deleteRequest.getIdAccount());

        if (account == null) {
            return ResponseHandler.generateResponse("No account found with id " + deleteRequest.getIdAccount(), HttpStatus.BAD_REQUEST);
        }

        try {
            service.deleteAccount(account, request.getHeader(Constant.PayloadResponseConstant.COOKIE));
            return ResponseHandler.generateResponse("Account deleted successfully", HttpStatus.OK);
        } catch (HttpServerErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/get-committee")
    public ResponseEntity<Object>  getCommitteeById(@RequestParam(value = "id", required = false) Integer id) {
        try {
            if(id == null) {
                return ResponseHandler.generateResponse("Get all committee succeed", HttpStatus.OK, service.getCommittee());
            }
            return ResponseHandler.generateResponse("Get committee succeed", HttpStatus.OK, service.getCommittee(id));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
