package com.jtk.ps.api.controller;
import com.jtk.ps.api.dto.CreateEvaluationRequest;
import com.jtk.ps.api.dto.UpdateEvaluationRequest;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    @Autowired
    @Lazy
    private ICompanyService companyService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvaluationDetail(
            @PathVariable("id") Integer idParticipant,
            @RequestParam(name = "numeval", required = true) Integer numEval,
            HttpServletRequest request) {
        try {
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            return ResponseHandler.generateResponse(
                    "Get evaluation detail succeed",
                    HttpStatus.OK,
                    companyService.getEvaluationDetail(cookie,idParticipant, numEval));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/form")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> getEvaluationForm(HttpServletRequest request,
                                                    @RequestParam(value = "id", required = true) Integer id,
                                                    @RequestParam(name = "prodi", required = true) Integer idProdi,
                                                    @RequestParam(name = "numeval", required = true) Integer numEvaluation){
        try {
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            return ResponseHandler.generateResponse("Get evaluation form succeed", HttpStatus.OK, companyService.getEvaluationForm(id,idProdi, numEvaluation, cookie));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get-by-participant")
    @PreAuthorize("hasAuthority('PARTICIPANT')")
    public ResponseEntity<Object> getEvaluationDetailByParticipant(HttpServletRequest request,
            @RequestParam(name = "numeval", required = true) Integer numEval) {
        try {
            Integer idParticipant = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            return ResponseHandler.generateResponse(
                    "Get evaluation detail succeed",
                    HttpStatus.OK,
                    companyService.getEvaluationDetail(cookie,idParticipant, numEval));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get-by-company")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> getEvaluationCardByCompany(HttpServletRequest request) {
        try {
            Integer idCompany = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            
            return ResponseHandler.generateResponse(
                    "Get evaluation by company succeed",
                    HttpStatus.OK,
                    companyService.getEvaluationCardByCompany(idCompany, cookie));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get-by-committee")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getEvaluationTableByCommittee(HttpServletRequest request) {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            
            return ResponseHandler.generateResponse(
                    "Get evaluation table by committee succeed",
                    HttpStatus.OK,
                    companyService.getEvaluationTableByCommittee(idProdi, cookie));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> createEvaluation(
            @RequestBody List<CreateEvaluationRequest> createEvaluationRequest) {
        try {
            companyService.createEvaluation(createEvaluationRequest);
            return ResponseHandler.generateResponse("Create evaluation succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> updateEvaluation(
            @RequestBody UpdateEvaluationRequest updateEvaluationRequest,
            @PathVariable("id") Integer id)
    {
        try {
            companyService.updateEvaluation(id, updateEvaluationRequest);
            return ResponseHandler.generateResponse("Update evaluation succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete-all")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> deleteAllEvaluation(HttpServletRequest request)
    {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            companyService.deleteAllEvaluation(idProdi);
            return ResponseHandler.generateResponse("Delete all evaluation succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/export-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public void exportPdf(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestParam(name = "numeval") Integer numEval,
                                            @RequestParam(name = "id") Integer idParticipant) {
        String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
        companyService.generatePdfEvaluation(idParticipant,response, cookie, numEval);
    }
}
