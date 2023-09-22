package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.FeedbackQuestionRequest;
import com.jtk.ps.api.service.IManagementContentService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/feedback-question")
public class FeedbackQuestionController {
    @Autowired
    @Lazy
    private IManagementContentService managementContentService;

    @GetMapping("/get-all/{id_prodi}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'COMPANY')")
    public ResponseEntity<Object> getAllFeedback(@PathVariable("id_prodi") Integer idProdi) {
        try {
            return ResponseHandler.generateResponse("Get all feedback's question succeed",
                    HttpStatus.OK,
                    managementContentService.getAllFeedbackQuestion(idProdi));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> createFeedbackQuestion(
            @RequestBody FeedbackQuestionRequest fqRequest,
            HttpServletRequest request)
    {
        try {
            Integer idProdi = (Integer) request.getAttribute(Constant.VerifyConstant.ID_PRODI);
            managementContentService.createFeedbackQuestion(idProdi, fqRequest);
            return ResponseHandler.generateResponse("Create feedback's question succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> updateFeedbackQuestion(
            @PathVariable("id") Integer id,
            @RequestBody FeedbackQuestionRequest fqRequest)
    {
        try {
            managementContentService.updateFeedbackQuestion(id, fqRequest);
            return ResponseHandler.generateResponse("Update feedback's question succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> deleteFeedbackQuestion(
            @PathVariable("id") Integer id)
    {
        try {
            managementContentService.deleteFeedbackQuestion(id);
            return ResponseHandler.generateResponse("Delete feedback's question succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
