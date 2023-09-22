package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.*;
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
@RequestMapping
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    @PostMapping(value = "/get-by-account")
    @PreAuthorize("hasAnyAuthority('COMMITTEE','COMPANY', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getAllCompaniesByAccountId(@RequestBody AccountIdRequest accountIdRequest) {
        try {
            List<Integer> listAccountId = accountIdRequest.getIdAccount();
            List<CompanyResponse> companyList = companyService.getAllCompanies(listAccountId);
            return ResponseHandler.generateResponse("Get all Companies by account id succeed", HttpStatus.OK, companyList);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllCompanies(
            @RequestParam(name = "type",required = false) String type,
            HttpServletRequest request)
    {
        try {
            if(Objects.equals(type, "dropdown")){
                Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
                
                return ResponseHandler.generateResponse(
                        "Success",
                        HttpStatus.OK,
                        companyService.getNameAndIdCompanies(idProdi));
            }
            List<CompanyResponse> companyList = companyService.getCompanies();
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, companyList);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id_company}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE', 'HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getCompanyById(@PathVariable("id_company") int id) {
        try {
            CompanyResponse companyResponse = companyService.getCompanyById(id);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, companyResponse);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-by-company")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> getCompanyByCompany(HttpServletRequest request) {
        try {
            Integer idCompany = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID));
            CompanyResponse companyResponse = companyService.getCompanyById(idCompany);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, companyResponse);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get-name")
    public ResponseEntity<Object> getCompanyNameById() {
        try {
            return ResponseHandler.generateResponse("Get company name succeed", HttpStatus.OK, companyService.getCompanyNameById());
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update-pic/{id_lecturer}")
    @PreAuthorize("hasAnyAuthority('COMMITTEE','HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> updateCompanyPic(@PathVariable("id_lecturer") Integer updatePicRequest) {
        try {
            companyService.updateCompanyPic(updatePicRequest);
            return ResponseHandler.generateResponse("Update company pic succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/list")
    @PreAuthorize("hasAnyAuthority('COMMITTEE','HEAD_STUDY_PROGRAM')")
    public ResponseEntity<Object> getListCompaniesByProdi(HttpServletRequest request) {
        try {
            ListCompany companyList = companyService.getCompaniesAndPrerequisiteByProdi((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Get list companies by prodi succeed", HttpStatus.OK, companyList);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> createCompany(@RequestBody CompanyRequest company, HttpServletRequest request) {
        try {
            companyService.createCompany(company, request.getHeader(Constant.PayloadResponseConstant.COOKIE));
            return ResponseHandler.generateResponse("Create company succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update/{id_company}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> updateCompany(@PathVariable("id_company") Integer idCompany, @RequestBody CompanyRequest company) {
        try {
            companyService.updateCompany(idCompany, company);
            return ResponseHandler.generateResponse("Update company succeed", HttpStatus.OK);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }   

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> updateCompany(@RequestBody CompanyRequest company, HttpServletRequest request) {
        try{
            companyService.updateCompany((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)), company);
            return ResponseHandler.generateResponse("Update company succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/req-company")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> getCompanyRequirements(HttpServletRequest request) {
        try {
            List<CompanyRequirement> companyRequirements = companyService.getCompanyRequirements(request.getHeader(Constant.PayloadResponseConstant.COOKIE), (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Get company requirement succeed", HttpStatus.OK, companyRequirements);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/change-status/{id_company}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> changeStatus(@PathVariable("id_company") Integer idCompany, HttpServletRequest request){
        try {

            Boolean bool = companyService.changeStatus(request.getHeader(Constant.PayloadResponseConstant.COOKIE), idCompany);
            if(Boolean.TRUE.equals(bool)){
                return ResponseHandler.generateResponse("Change status succeed", HttpStatus.OK);
            }else{
                return ResponseHandler.generateResponse("Change status failed", HttpStatus.BAD_REQUEST);
            }
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
