package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.CompetenceRequest;
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
@RequestMapping("/competence")
public class CompetenceController {
    @Autowired
    @Lazy
    private IManagementContentService managementContentService;

    @GetMapping("/get-type")
    public ResponseEntity<Object> getTypeCompetence(@RequestParam("id") Integer id) {
        try {
            return ResponseHandler.generateResponse("Get type competence succeed", HttpStatus.OK, managementContentService.getTypeCompetence(id));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all/{id_prodi}")
    public ResponseEntity<Object> getAllCompetence(@PathVariable("id_prodi") Integer idProdi, HttpServletRequest request) {
        try {
            return ResponseHandler.generateResponse("Get all competence succeed", HttpStatus.OK, managementContentService.getAllCompetence(idProdi));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get-all-type")
    public ResponseEntity<Object> getAllTypeCompetence() {
        try {
            return ResponseHandler.generateResponse("Get all type competence succeed", HttpStatus.OK, managementContentService.getAllTypeCompetence());
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<Object> createCompetence(
            @RequestBody CompetenceRequest competenceRequest,
            HttpServletRequest request)
    {
        try {
            String picName = (String) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.NAME));
            managementContentService.createCompetence(competenceRequest, picName);
            return ResponseHandler.generateResponse("Create competence succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> updateCompetence(
            @PathVariable("id") Integer id,
            @RequestBody CompetenceRequest competenceRequest,
            HttpServletRequest request)
    {
        try {
            String picName = (String) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.NAME));
            managementContentService.updateCompetence(id, competenceRequest, picName);
            return ResponseHandler.generateResponse("Update competence succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> deleteCompetence(
            @PathVariable("id") Integer id, HttpServletRequest request)
    {
        try {
            managementContentService.deleteCompetence(id,
                    request.getHeader(Constant.PayloadResponseConstant.COOKIE));
            return ResponseHandler.generateResponse("Delete competence succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
