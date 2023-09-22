package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.dto.cv.CVCommitteeResponse;
import com.jtk.ps.api.dto.cv.CVCompanyResponse;
import com.jtk.ps.api.dto.cv.CVParticipantResponse;
import com.jtk.ps.api.model.AbsenceRecap;
import com.jtk.ps.api.model.Participant;
import com.jtk.ps.api.service.IParticipantService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("")
public class ParticipantController {

    @Autowired
    private IParticipantService participantService;

    @PostMapping(value = "/get-by-id")
    public ResponseEntity<Object> getParticipantById(@RequestBody ListIdRequest listIdRequest) {
        try {
            List<Participant> listParticipant = participantService.getParticipantById(listIdRequest.getId());
            List<ParticipantResponse> participantResponseList = participantListToParticipantResponse(listParticipant);

            return ResponseHandler.generateResponse("Get all participant succeed", HttpStatus.OK, participantResponseList);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/get-by-account")
    public ResponseEntity<Object> getByAccountId(@RequestBody ListIdRequest accountIdRequest) {
        try {
            List<Integer> listAccountId = accountIdRequest.getId();
            List<Participant> listParticipant = participantService.getParticipantByAccountId(listAccountId);
            List<ParticipantResponse> participantResponseList = participantListToParticipantResponse(listParticipant);

            return ResponseHandler.generateResponse("Get all participant succeed", HttpStatus.OK, participantResponseList);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<Object> getParticipant(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "type", required = false) String type,
            HttpServletRequest request)
    {
        try {
            if(Objects.equals(type, "dropdown")){
                Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
                
                return ResponseHandler.generateResponse(
                        "Success",
                        HttpStatus.OK,
                        participantService.getNameAndIdCompanies(idProdi));
            }
            
            List<Participant> listParticipant = participantService.getParticipantByYear(year, (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            List<ParticipantResponse> listParticipantResponse = participantListToParticipantResponse(listParticipant);
            return ResponseHandler.generateResponse("Get all participant succeed", HttpStatus.OK, listParticipantResponse);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cv-by-participant")
    @PreAuthorize("hasAuthority('PARTICIPANT')")
    public ResponseEntity<Object> getParticipantCV(HttpServletRequest request) {
        try {
            CVParticipantResponse cvParticipantResponse = participantService.getCVParticipant((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)), request.getHeader(Constant.PayloadResponseConstant.COOKIE));
            return ResponseHandler.generateResponse("Get all participant succeed", HttpStatus.OK, cvParticipantResponse);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cv-by-committee")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> getParticipantCVByCommittee(HttpServletRequest request) {
        try {
            CVCommitteeResponse cvCommitteeResponse = participantService.getCVParticipantByCommittee((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Get all participant succeed", HttpStatus.OK, cvCommitteeResponse);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cv-by-company")
    @PreAuthorize("hasAuthority('COMPANY')")
    public ResponseEntity<Object> getParticipantCVByCompany(HttpServletRequest request) {
        try {
            List<CVCompanyResponse> cvCompanyResponse = participantService.getCVParticipantByCompany((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID)), request.getHeader(Constant.PayloadResponseConstant.COOKIE));
            return ResponseHandler.generateResponse("Get all participant succeed", HttpStatus.OK, cvCompanyResponse);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cv-interest-participant")
    public ResponseEntity<Object> getCVInterestParticipant(HttpServletRequest request) {
        try {
            List<CVInterestResponse> cvInterestResponse = participantService.getCVInterestParticipant(request.getHeader(Constant.PayloadResponseConstant.COOKIE), (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Get all participant succeed", HttpStatus.OK, cvInterestResponse);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/participant-value")
    public ResponseEntity<Object> getParticipantValue(HttpServletRequest request) {
        try {
            List<ParticipantValueList> participantValueResponse = participantService.getParticipantValue((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Get all participant succeed", HttpStatus.OK, participantValueResponse);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all-absence")
    public ResponseEntity<Object> getAbsenceRecap(HttpServletRequest request) {
        try {
            List<AbsenceRecap> absenceRecap = participantService.getAllAbsence((Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Get all participant succeed", HttpStatus.OK, absenceRecap);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<ParticipantResponse> participantListToParticipantResponse(List<Participant> participantList) {
        List<ParticipantResponse> listParticipantResponse = new ArrayList<>();
        if (participantList != null) {
            for (Participant i : participantList) {
                ParticipantResponse participantResponse = new ParticipantResponse();
                participantResponse.setNim(i.getId());
                participantResponse.setIdAccount(i.getAccountId());
                participantResponse.setIdCv(i.getCv().getId());
                participantResponse.setName(i.getName());
                participantResponse.setIdProdi(i.getProdiId());
                participantResponse.setIpk(i.getIpk());
                participantResponse.setWorkSystem(i.getWorkSystem());
                participantResponse.setYear(i.getYear());
                participantResponse.setStatusCv(i.getStatusCv());
                participantResponse.setStatusInterest(i.getStatusInterest());
                participantResponse.setIdParticipant(i.getId());

                listParticipantResponse.add(participantResponse);
            }
        }

        return listParticipantResponse;
    }
}
