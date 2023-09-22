package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.JobscopeRequest;
import com.jtk.ps.api.service.IManagementContentService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/jobscope")
public class JobscopeController {
    @Autowired
    @Lazy
    private IManagementContentService managementContentService;

    @GetMapping("/get-all/{id_prodi}")
    public ResponseEntity<Object> getAllJobscope(@PathVariable("id_prodi") Integer idProdi, HttpServletRequest request) {
        try {
            return ResponseHandler.generateResponse("Get all Jobscope succeed", HttpStatus.OK, managementContentService.getAllJobscope(idProdi));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<Object> createJobscope(
            @RequestBody JobscopeRequest jobscopeRequest,
            HttpServletRequest request)
    {
        try {
            String picName = (String) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.NAME));
            managementContentService.createJobscope(jobscopeRequest, picName);
            return ResponseHandler.generateResponse("Create jobscope succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> updateJobscope(
            @PathVariable("id") Integer id,
            @RequestBody JobscopeRequest jobscopeRequest,
            HttpServletRequest request)
    {
        try {
            String picName = (String) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.NAME));
            managementContentService.updateJobscope(id, jobscopeRequest, picName);
            return ResponseHandler.generateResponse("Update jobscope succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> deleteJobscope(
            @PathVariable("id") Integer id, HttpServletRequest request)
    {
        try {
            managementContentService.deleteJobscope(id,
                    request.getHeader(Constant.PayloadResponseConstant.COOKIE));
            return ResponseHandler.generateResponse("Delete jobscope succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
