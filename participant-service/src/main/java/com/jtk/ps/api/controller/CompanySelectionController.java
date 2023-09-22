package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.ParticipantCompany;
import com.jtk.ps.api.service.IParticipantService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/company-selection")
public class CompanySelectionController {
    @Autowired
    private IParticipantService participantService;


    @GetMapping("")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> getCompanySelection(HttpServletRequest request) {
        try {
            CompanySelectionResponse companySelection = participantService.getCompanySelection(request.getHeader(Constant.PayloadResponseConstant.COOKIE), (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Get Company Selection succeed", HttpStatus.OK,companySelection);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mapping")
    public ResponseEntity<Object> getCompanySelectionMapping(HttpServletRequest request) {
        try {
            List<ParticipantCompany> companySelectionMapping = participantService.getCompanySelectionMapping((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Get Company Selection Mapping succeed", HttpStatus.OK,companySelectionMapping);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/card")
    @PreAuthorize("hasAuthority('PARTICIPANT')")
    public ResponseEntity<Object> getCompanySelectionCard(HttpServletRequest request) {
        try {
            CompanySelectionCard companySelectionCard = participantService.getCompanySelectionCard((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)),request.getHeader(Constant.PayloadResponseConstant.COOKIE));
            return ResponseHandler.generateResponse("Get Company Selection Card succeed", HttpStatus.OK,companySelectionCard);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail")
    @PreAuthorize("hasAuthority('PARTICIPANT')")
    public ResponseEntity<Object> getCompanySelectionDetail(HttpServletRequest request) {
        try {
            CompanySelectionDetail companySelectionDetail = participantService.getCompanySelectionDetail((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)),request.getHeader(Constant.PayloadResponseConstant.COOKIE));
            return ResponseHandler.generateResponse("Get Company Selection Detail succeed", HttpStatus.OK, companySelectionDetail);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('PARTICIPANT')")
    public ResponseEntity<Object> updateCompanySelection(@RequestBody CompanySelectionUpdate companySelectionUpdate, HttpServletRequest request) {
        try {
            Boolean isSuccess = participantService.updateCompanySelection((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)), companySelectionUpdate);
            if(Boolean.TRUE.equals(isSuccess)){
                return ResponseHandler.generateResponse("Update Company Selection succeed", HttpStatus.OK);
            }else{
                return ResponseHandler.generateResponse("Update Company Selection failed", HttpStatus.BAD_REQUEST);
            }
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/mark-as-done")
    @PreAuthorize("hasAuthority('PARTICIPANT')")
    public ResponseEntity<Object> markAsDoneInterest(HttpServletRequest request) {
        try {
            Boolean isSuccess = participantService.markAsDoneInterest((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)));
            if(Boolean.TRUE.equals(isSuccess)){
                return ResponseHandler.generateResponse("Mark as done Company Selection succeed", HttpStatus.OK);
            }else{
                return ResponseHandler.generateResponse("Mark as done Company Selection failed", HttpStatus.BAD_REQUEST);
            }
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recap")
    @PreAuthorize("hasAnyAuthority('COMMITTEE','HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getCompanySelectionRecap(HttpServletRequest request) {
        try {
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            List<CompanySelectionRecap> companySelectionRecap = participantService.getCompanySelectionRecap(cookie);
            return ResponseHandler.generateResponse("Get Company Selection Recap succeed", HttpStatus.OK,companySelectionRecap);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
