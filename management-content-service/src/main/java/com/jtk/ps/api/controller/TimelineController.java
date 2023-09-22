package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.TimelineRequest;
import com.jtk.ps.api.model.TimelineSetting;
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
import java.util.Objects;

@RestController
@RequestMapping("/timeline")
public class TimelineController {

    @Autowired
    private IManagementContentService managementContentService;


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> createTimeline(@RequestBody TimelineRequest timelineRequest, HttpServletRequest request) {
        try {
            TimelineSetting timelineSetting = managementContentService.createTimeline(timelineRequest, (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Create timeline Successfully", HttpStatus.OK, timelineSetting);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> getTimeline(HttpServletRequest request){
        try {
            Integer idProdi = (Integer) request.getAttribute(Constant.VerifyConstant.ID_PRODI);
            if(idProdi != null){
                return ResponseHandler.generateResponse("Get timeline Successfully", HttpStatus.OK, managementContentService.getTimeline(idProdi));
            }
            return ResponseHandler.generateResponse("Get timeline Successfully", HttpStatus.OK, managementContentService.getTimeline());
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> updateTimeline(@PathVariable("id") Integer id,@RequestBody TimelineRequest timelineRequest, HttpServletRequest request) {
        try {
            managementContentService.updateTimeline(id,timelineRequest, (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI)));
            return ResponseHandler.generateResponse("Update timeline Successfully", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('COMMITTEE')")
    public ResponseEntity<Object> deleteTimeline(@PathVariable("id") Integer id,HttpServletRequest request) {
        try {
            managementContentService.deleteTimeline(id);
            return ResponseHandler.generateResponse("Delete timeline Successfully", HttpStatus.OK);
        }catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
