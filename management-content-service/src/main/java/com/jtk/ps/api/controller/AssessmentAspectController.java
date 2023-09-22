package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.AssessmentAspectRequest;
import com.jtk.ps.api.model.EProdi;
import com.jtk.ps.api.service.IManagementContentService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/assessment-aspect")
public class AssessmentAspectController {
    @Autowired
    private IManagementContentService managementContentService;
    
    @GetMapping("/get-form")
    public ResponseEntity<Object> getAssessmentAspectForm(
            @RequestParam(name = "prodi", required = true) Integer idProdi,
            @RequestParam(name = "numeval", required = true) Integer numEvaluation){
        try {
            return ResponseHandler.generateResponse(
                    "Get assessment aspect form Successfully",
                    HttpStatus.OK,
                    managementContentService.getAssessmentAspectForm(idProdi, numEvaluation));
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> getAssessmentAspect(HttpServletRequest request){
        try {
            Integer idProdi = (Integer) request.getAttribute(Constant.VerifyConstant.ID_PRODI);
            if (idProdi == EProdi.D3.id) {
                return ResponseHandler.generateResponse(
                        "Get assessment aspect Successfully",
                        HttpStatus.OK,
                        managementContentService.getAssessmentAspectD3());
            }
            
            return ResponseHandler.generateResponse(
                    "Get assessment aspect Successfully",
                    HttpStatus.OK,
                    managementContentService.getAssessmentAspectD4());
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> createAssessmentAspect(
            @RequestBody AssessmentAspectRequest assessmentAspectRequest) {
        try {
            managementContentService.createAssessmentAspect(assessmentAspectRequest);
            return ResponseHandler.generateResponse("Create assessment aspect Successfully", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> updateAssessmentAspect(
            @PathVariable("id") Integer id,
            @RequestBody AssessmentAspectRequest assessmentAspectRequest) {
        try {
            managementContentService.updateAssessmentAspect(id, assessmentAspectRequest);
            return ResponseHandler.generateResponse("Update assessment aspect Successfully", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> deleteAssessmentAspect(
            @PathVariable("id") Integer id) {
        try {
            managementContentService.deleteAssessmentAspect(id);
            return ResponseHandler.generateResponse("Delete assessment aspect Successfully", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
