package com.jtk.ps.api.controller;
import com.jtk.ps.api.dto.SubmissionRequest;
import com.jtk.ps.api.service.ICompanyService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/submission")
public class SubmissionController {
    @Autowired
    @Lazy
    private ICompanyService companyService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> getCompanySubmission() {
        try {
            return ResponseHandler.generateResponse("Get company submission succeed", HttpStatus.OK, companyService.getCompanySubmission());
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/detail/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> getDetailCompanySubmission(@PathVariable("id") Integer id) {
        try {
            return ResponseHandler.generateResponse("Get company submission succeed", HttpStatus.OK, companyService.getDetailCompanySubmission(id));
        } catch (HttpClientErrorException ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<Object> createCompanySubmission(@RequestBody SubmissionRequest submissionRequest)
    {
        try {
            companyService.createCompanySubmission(submissionRequest);
            return ResponseHandler.generateResponse("Create company submission succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/accept/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> acceptCompanySubmission(
            @PathVariable("id") Integer id,
            HttpServletRequest request)
    {
        try {
            companyService.acceptCompanySubmission(id,
                    request.getHeader(Constant.PayloadResponseConstant.COOKIE));
            return ResponseHandler.generateResponse("Accept company submission succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/decline/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> declineCompanySubmission(@PathVariable("id") Integer id) {
        try {
            companyService.declineCompanySubmission(id);
            return ResponseHandler.generateResponse("Decline company submission succeed", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
