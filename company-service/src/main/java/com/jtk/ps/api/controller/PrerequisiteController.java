package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.PrerequisiteRecapResponse;
import com.jtk.ps.api.dto.PrerequisiteRequest;
import com.jtk.ps.api.dto.QuotaResponse;
import com.jtk.ps.api.service.ICompanyService;
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
@RequestMapping("/prerequisite")
public class PrerequisiteController {
    @Autowired
    private ICompanyService companyService;

    @GetMapping("/card-by-company")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> getCardPrerequisiteByCompany(HttpServletRequest request) {
        try {
            return ResponseHandler.generateResponse("Get Card Prerequisite succeed", HttpStatus.OK, companyService.getCardPrerequisiteByCompany((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)), request.getHeader(Constant.PayloadResponseConstant.COOKIE)));
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/card-by-committee")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getCardPrerequisiteByCommittee(@RequestParam("id-company") Integer idCompany,HttpServletRequest request) {
        try {
            return ResponseHandler.generateResponse("Get Card Prerequisite succeed", HttpStatus.OK, companyService.getCardPrerequisiteByCommittee(request.getHeader(Constant.PayloadResponseConstant.COOKIE), idCompany));
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id_prerequisite}")
    @PreAuthorize("hasAnyAuthority('COMPANY','COMMITTEE','HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getPrerequisite(@PathVariable("id_prerequisite") Integer idPrerequisite, HttpServletRequest request) {
        try {
            return ResponseHandler.generateResponse("Get Prerequisite succeed", HttpStatus.OK,companyService.getPrerequisite(idPrerequisite, request.getHeader(Constant.PayloadResponseConstant.COOKIE)));
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/prerequisites-company")
    @PreAuthorize("hasAnyAuthority('PARTICIPANT','HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getPrerequisiteCompany(HttpServletRequest request) {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            return ResponseHandler.generateResponse("Get Prerequisites succeed", HttpStatus.OK, companyService.getPrerequisitesCompany(idProdi, request.getHeader(Constant.PayloadResponseConstant.COOKIE) ));
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("quota")
    public ResponseEntity<Object> getQuota(
            HttpServletRequest request)
    {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            List<QuotaResponse> pr = companyService.getQuota();
            return ResponseHandler.generateResponse("Get Prerequisite succeed", HttpStatus.OK, pr);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/company")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> updatePrerequisiteByCompany(@RequestBody PrerequisiteRequest prerequisite, HttpServletRequest request) {
        try {
            Boolean isSuccess = companyService.updatePrerequisiteByCompany(prerequisite, (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)));
            if(Boolean.TRUE.equals(isSuccess)){
                return ResponseHandler.generateResponse("Update Prerequisite succeed", HttpStatus.OK);
            }else{
                return ResponseHandler.generateResponse("Update prerequisite failed", HttpStatus.BAD_REQUEST);
            }
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/committee/{id_prerequisite}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> updatePrerequisiteByCommittee(@PathVariable("id_prerequisite") Integer idPrerequisite, @RequestBody PrerequisiteRequest prerequisite, HttpServletRequest request) {
        try {
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            Boolean isSuccess = companyService.updatePrerequisiteByCommittee(idPrerequisite,prerequisite, cookie);
            if(Boolean.TRUE.equals(isSuccess)){
                return ResponseHandler.generateResponse("Update Prerequisite succeed", HttpStatus.OK);
            }else{
                return ResponseHandler.generateResponse("Update Prerequisite failed", HttpStatus.BAD_REQUEST);
            }

        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/committee/mark-as-done/{id_prerequisite}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> markAsDone(@PathVariable("id_prerequisite") Integer idPrerequisite, HttpServletRequest request) {
        try {
            Boolean isSuccess = companyService.markAsDoneByCommittee(idPrerequisite);
            if(Boolean.TRUE.equals(isSuccess)){
                return ResponseHandler.generateResponse("Mark as done succeed", HttpStatus.OK);
            }else{
                return ResponseHandler.generateResponse("Mark as done failed", HttpStatus.BAD_REQUEST);
            }
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/company/mark-as-done")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> markAsDoneByCompany(HttpServletRequest request) {
        try {
            Boolean isSuccess = companyService.markAsDoneByCompany((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)));
            if(Boolean.TRUE.equals(isSuccess)){
                return ResponseHandler.generateResponse("Mark as done succeed", HttpStatus.OK);
            }else {
                return ResponseHandler.generateResponse("Mark as done failed", HttpStatus.BAD_REQUEST);
            }
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete-competence/{id}")
    public ResponseEntity<Object> deletePrerequisiteCompetence(@PathVariable("id") Integer id) {
        try {
            companyService.deletePrerequisiteCompetence(id);
            return ResponseHandler.generateResponse("Delete prerequisite competence succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping(value = "/delete-jobscope/{id}")
    public ResponseEntity<Object> deletePrerequisiteJobscope(@PathVariable("id") Integer id) {
        try {
            companyService.deletePrerequisiteJobscope(id);
            return ResponseHandler.generateResponse("Delete prerequisite jobscope succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recap")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> recapPrerequisite(HttpServletRequest request) {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            List<PrerequisiteRecapResponse> pr = companyService.recapPrerequisite(cookie, idProdi);
            return ResponseHandler.generateResponse("Get Prerequisite succeed", HttpStatus.OK, pr);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
