package com.jtk.ps.api.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jtk.ps.api.dto.ComponentAndCriteriasDto;
import com.jtk.ps.api.dto.ComponentCourseDto;
import com.jtk.ps.api.dto.CourseFormRequestDto;
import com.jtk.ps.api.service.Interface.ICourseService;
import com.jtk.ps.api.util.Constant;
import com.jtk.ps.api.util.ResponseHandler;

@RestController
@RequestMapping("/courses")
public class CourseController {
    
    @Autowired
    private ICourseService courseService;
    
    @GetMapping("/form") //checked
    public ResponseEntity<Object> getAllCourseForm(HttpServletRequest request){
        Integer roleId = (Integer) Objects.requireNonNull(request.getAttribute(Constant.VerifyConstant.ID_ROLE));
        return ResponseHandler.generateResponse("Get all Course Form succeed",HttpStatus.OK, courseService.getAllCourse(roleId));
    }

    @PostMapping("/form") //checked
    public ResponseEntity<Object> createCourseForm(@RequestBody CourseFormRequestDto newCourse){
        courseService.createCourseForm(newCourse);
        return ResponseHandler.generateResponse("Create Course Form succeed",HttpStatus.OK);
    }

    @GetMapping("/form/{idForm}") // checked
    public ResponseEntity<Object> getDetailCourse(@PathVariable("idForm") Integer idForm){
        return ResponseHandler.generateResponse("Get Detail Course succeed",HttpStatus.OK, courseService.getDetailCourse(idForm));
    }

    @PutMapping("/form/update/{idForm}") //checked
    public ResponseEntity<Object> updateCourseForm(@PathVariable("idForm") Integer idForm,@RequestBody CourseFormRequestDto newCourseForm){
        courseService.updateCourseForm(idForm, newCourseForm);
        return ResponseHandler.generateResponse("Update Course Form succeed",HttpStatus.OK);
    }

    @DeleteMapping("/form/delete/{idForm}") //checked
    public ResponseEntity<Object> deleteCourseForm(@PathVariable("idForm") Integer idForm){
        courseService.deleteCourseForm(idForm);
        return ResponseHandler.generateResponse("Deleted Course Form succeed",HttpStatus.OK);
    }

    @GetMapping("/criteria/evaluation-form/{prodiId}")//checked
    public ResponseEntity<Object> getEvaluationFormByProdiId(@PathVariable("prodiId") Integer prodiId){
        return ResponseHandler.generateResponse("Get Evaluation Forms succeed",HttpStatus.OK, courseService.getEvaluationForm(prodiId));
    }

    @GetMapping("/criteria/evaluation-form/aspect") // checked
    public ResponseEntity<Object> getAspectEvaluationForm(@RequestParam("formType") String formType,@RequestParam("prodiId") Integer prodiId){
        return ResponseHandler.generateResponse("Get Aspects Evaluation succeed",HttpStatus.OK, courseService.getCriteriaByEvaluationForm(formType,prodiId));
    }

    @GetMapping("/criteria/evaluation-form/aspect/type") // checked
    public ResponseEntity<Object> getTypeAspectEvaluationForm(@RequestParam("formType") String formType,@RequestParam("prodiId") Integer prodiId){
        return ResponseHandler.generateResponse("Get Type from Aspects Evaluation succeed",HttpStatus.OK, courseService.getTypeAspectEvaluationForm(formType, prodiId));
    }

    @GetMapping("/component/course-form/{idForm}") // checked
    public ResponseEntity<Object> getComponentByCourseForm(@PathVariable("idForm") Integer idForm){
        return ResponseHandler.generateResponse("Get All Component By Course Form Id succeed",HttpStatus.OK, courseService.getComponentByCourseForm(idForm));
    }

    @PutMapping("/component/update") // checked
    public ResponseEntity<Object> updateComponentCourse(@RequestBody List<ComponentCourseDto> newComponentCourses){
        courseService.updateComponent(newComponentCourses);
        return ResponseHandler.generateResponse("Update Component Course Form succeed",HttpStatus.OK);
    }

    @GetMapping("/component/criteria/form/{idForm}") //checked
    public ResponseEntity<Object> getCriteriaComponentByCourseFormId(@PathVariable("idForm") Integer idForm){
        return ResponseHandler.generateResponse("Get All Component By Course Form Id succeed",HttpStatus.OK, courseService.getCriteriaComponentByCourseFormId(idForm));
    }

    @PutMapping("/component/criteria/update") //checked
    public ResponseEntity<Object> updateAllCriteriaInComponentCourse(@RequestBody ComponentAndCriteriasDto newCriterias){
        courseService.updateOrInsertCriteriaComponent(newCriterias);
        return ResponseHandler.generateResponse("Update Criteria in Component Course Form succeed",HttpStatus.OK);
    }

    @GetMapping("/recapitulation") 
    public ResponseEntity<Object> getRecapitulationCourseByYearAndProdi(@RequestParam("year") Integer year, @RequestParam("prodiId") Integer prodiId){
        return ResponseHandler.generateResponse("Get Recapitulation By Year And ProdiId succeed",HttpStatus.OK, courseService.getAllRecapitulationByYearAndProdiId(year, prodiId));
    }

    @PostMapping("/form/finalization") //checked
    public ResponseEntity<Object> finalizationAllCourse(){
        courseService.finalizationAllCourseForm();
        return ResponseHandler.generateResponse("Finalization All Course Form succeed",HttpStatus.OK);
    }

    @GetMapping("/generate-course")
    public ResponseEntity<Resource> getXLS(@RequestParam("year") Integer year, @RequestParam("prodiId") Integer prodiId) {
        String filename = "rekapitulasi mata kuliah "+year+".xlsx";
        
        InputStreamResource file = new InputStreamResource(courseService.loadCourse(year, prodiId));
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(file);
    }
}
