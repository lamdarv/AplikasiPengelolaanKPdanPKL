package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.CriteriaRequest;
import com.jtk.ps.api.service.ICompanyService;
import com.jtk.ps.api.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/criteria")
public class CriteriaController {
    @Autowired
    @Lazy
    private ICompanyService companyService;

    @GetMapping("")
    public ResponseEntity<Object> getCompanyCriteria() {
        try {
            return ResponseHandler.generateResponse("Get all company's criteria succeed", HttpStatus.OK, companyService.getCompanyCriteria());
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> createCriteria(@RequestBody CriteriaRequest criteriaRequest)
    {
        try {
            companyService.createCriteria(criteriaRequest);
            return ResponseHandler.generateResponse("Create company's criteria succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> updateCriteria(
            @PathVariable("id") Integer id,
            @RequestBody CriteriaRequest criteriaRequest)
    {
        try {
            companyService.updateCriteria(id, criteriaRequest);
            return ResponseHandler.generateResponse("Update company's criteria succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> deleteCriteria(@PathVariable("id") Integer id) {
        try {
            companyService.deleteCriteria(id);
            return ResponseHandler.generateResponse("Delete company's criteria succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
