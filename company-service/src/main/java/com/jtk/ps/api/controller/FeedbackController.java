package com.jtk.ps.api.controller;
import com.jtk.ps.api.dto.CreateFeedbackRequest;
import com.jtk.ps.api.dto.UpdateFeedbackRequest;
import com.jtk.ps.api.service.ICompanyService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    @Lazy
    private ICompanyService companyService;
    
    @GetMapping("/get-by-company")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> getFeedbackCardByCompany(HttpServletRequest request) {
        try {
            Integer idCompany = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            
            return ResponseHandler.generateResponse(
                    "Get feedback by company succeed",
                    HttpStatus.OK,
                    companyService.getFeedbackCardByCompany(idCompany, cookie));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get-by-committee")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getFeedbackTableByCommittee(HttpServletRequest request) {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            
            return ResponseHandler.generateResponse(
                    "Get evaluation table by committee succeed",
                    HttpStatus.OK,
                    companyService.getFeedbackTableByCommittee(idProdi, cookie));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id_company}/{id_prodi}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM', 'COMPANY')")
    public ResponseEntity<Object> getFeedbackDetail(
            @PathVariable("id_company") Integer idCompany,
            @PathVariable("id_prodi") Integer idProdi) {
        try {
            return ResponseHandler.generateResponse(
                    "Get feedback's detail succeed",
                    HttpStatus.OK,
                    companyService.getFeedbackDetail(idCompany, idProdi));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> createFeedback(
            @RequestBody List<CreateFeedbackRequest> createFeedbackRequest) {
        try {
            companyService.createFeedback(createFeedbackRequest);
            return ResponseHandler.generateResponse("Create feedback succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/{id_prodi}")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> updateFeedback(
            @RequestBody List<UpdateFeedbackRequest> updateFeedbackRequest,
            @PathVariable("id_prodi") Integer idProdi,
            HttpServletRequest request)
    {
        try {
            Integer idCompany = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID));
            companyService.updateFeedback(idCompany, idProdi, updateFeedbackRequest);
            return ResponseHandler.generateResponse("Update feedback succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/export-pdf")
    public ResponseEntity<Object> exportPdf(HttpServletRequest request, HttpServletResponse response) {
        try {
//            companyService.generatePdfFeedback(idParticipant,response);
            return null;
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
