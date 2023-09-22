package com.jtk.ps.api.controller;

import com.jtk.ps.api.dto.cv.CVGetResponse;
import com.jtk.ps.api.model.Course;
import com.jtk.ps.api.service.IParticipantService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RequestMapping("/course")
@RestController
public class CourseController {
    @Autowired
    private IParticipantService participantService;

    @RequestMapping("")
    public ResponseEntity<Object> getCourse(HttpServletRequest request) {
        try {
            Integer idProdi = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_PRODI));
            List<Course> cv = participantService.getAllCourse(idProdi);
            return ResponseHandler.generateResponse("Get CV succeed", HttpStatus.OK, cv);
        } catch (HttpClientErrorException ex){
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
