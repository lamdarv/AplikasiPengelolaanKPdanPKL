package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.FinalMappingRequest;
import com.jtk.ps.api.dto.FinalMappingResponse;
import com.jtk.ps.api.dto.ParticipantFinalMappingResponse;
import com.jtk.ps.api.dto.ranking.RankingAndDateResponse;
import com.jtk.ps.api.dto.ranking.RankingResponse;
import com.jtk.ps.api.service.IMappingService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.model.CriteriaMapping;
import com.jtk.ps.api.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("")
public class MappingController {
    @Autowired
    private IMappingService mappingService;

    @GetMapping("/final/company/{id_company}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE','COMPANY','HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getParticipantInFinalMapping(@PathVariable("id_company") Integer idCompany) {
        try {
            List<ParticipantFinalMappingResponse> listParticipant = mappingService.getParticipantFinalMapping(idCompany);
            return ResponseHandler.generateResponse("Get Participant in final mapping succeed", HttpStatus.OK, listParticipant);
        }catch (HttpClientErrorException ex) {
            List<ParticipantFinalMappingResponse> listParticipant = mappingService.getParticipantFinalMapping(idCompany);
            return ResponseHandler.generateResponse("Get Participant in final mapping succeed", HttpStatus.OK, listParticipant);
        }
    }
    
    @GetMapping("/final")
    public ResponseEntity<Object> getFinalMapping(HttpServletRequest request) {
        try {
            Integer idRole = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_ROLE));
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            
            FinalMappingResponse finalMapping = mappingService.getFinalMapping(idRole, idProdi, cookie);
            
            return ResponseHandler.generateResponse("Get final mapping data succeed", HttpStatus.OK, finalMapping);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/final/create")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> createFinalMapping(
            @RequestBody FinalMappingRequest finalMappingRequest,
            HttpServletRequest request)
    {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            mappingService.createFinalMapping(finalMappingRequest, idProdi);
            return ResponseHandler.generateResponse("Create final mapping succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/final/delete/{id_participant}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> deleteFinalMapping(
            @PathVariable("id_participant") Integer idParticipant,
            HttpServletRequest request)
    {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            mappingService.deleteFinalMapping(idParticipant, idProdi);
            return ResponseHandler.generateResponse("Delete final mapping succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/final/submit")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> submitFinalMapping(HttpServletRequest request)
    {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            
            mappingService.submitFinalMapping(idProdi, cookie);
            return ResponseHandler.generateResponse("Submit final mapping succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/final/publish")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> submitPublishMapping(HttpServletRequest request)
    {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            mappingService.submitPublishMapping(idProdi, cookie);
            return ResponseHandler.generateResponse("Submit publish mapping to company succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/generate-rank")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> generateRank(HttpServletRequest request) {
        try {
            Boolean isSuccess = mappingService.generateRank(request.getHeader(Constant.PayloadResponseConstant.COOKIE), (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            if(isSuccess){
                return ResponseHandler.generateResponse("Generate rank succeed", HttpStatus.OK);
            }else{
                return ResponseHandler.generateResponse("Generate rank failed", HttpStatus.BAD_REQUEST);
            }
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-rank")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> getParticipantRanking(HttpServletRequest request) {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            RankingAndDateResponse rankingResponseList = mappingService.getRanking(request.getHeader(Constant.PayloadResponseConstant.COOKIE),idProdi);
            return ResponseHandler.generateResponse("Get all mapping succeed", HttpStatus.OK, rankingResponseList);
        }catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/criteria")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> getCriteria() {
        try {
            return ResponseHandler.generateResponse("Get all criteria succeed", HttpStatus.OK, mappingService.getCriteria());
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/criteria/update")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> updateCriteria(@RequestBody List<CriteriaMapping> criteriaRequest) {
        try {
            mappingService.updateCriteria(criteriaRequest);
            return ResponseHandler.generateResponse("Get update criteria succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/export-mapping")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> exportMapping(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            mappingService.exportMapping(cookie,response,idProdi);
            return null;
        }catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get-is-final/{id}")
    public ResponseEntity<Object> getIsFinalMapping(@PathVariable("id") Integer id) {
        try {
            return ResponseHandler.generateResponse("Get is final mapping succeed", HttpStatus.OK, mappingService.getIsFinalMapping(id));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/final/delete-company/{id_company}")
    public ResponseEntity<Object> deleteCompany(@PathVariable("id_company") Integer idCompany, @RequestParam(name = "prodi", required = false) Integer idProdi) {
        try {
            if(idProdi == null) {
                mappingService.deleteCompany(idCompany);
            }
            mappingService.deleteCompanyByProdi(idCompany, idProdi);
            return ResponseHandler.generateResponse("Delete company succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-participant-by-company/{id_company}")
    public ResponseEntity<Object> getParticipantByCompany(@PathVariable("id_company") Integer idCompany, HttpServletRequest request) {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            String cookie = request.getHeader(Constant.PayloadResponseConstant.COOKIE);
            return ResponseHandler.generateResponse("Get participant by company succeed", HttpStatus.OK, mappingService.getParticipantByCompany(idCompany, cookie, idProdi));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
