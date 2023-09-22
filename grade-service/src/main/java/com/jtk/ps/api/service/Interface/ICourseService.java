package com.jtk.ps.api.service.Interface;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.jtk.ps.api.dto.ComponentCourseDto;
import com.jtk.ps.api.dto.ComponentAndCriteriasDto;
import com.jtk.ps.api.dto.CourseFormRequestDto;
import com.jtk.ps.api.dto.CourseFormResponseDto;
import com.jtk.ps.api.dto.CriteriaEvaluationFormDto;
import com.jtk.ps.api.dto.EvaluationFormResponseDto;
import com.jtk.ps.api.dto.RecapitulationCourseDto;
import com.jtk.ps.api.dto.TypeOfAspectEvaluationDto;
import com.jtk.ps.api.model.CourseForm;

public interface ICourseService {
    
    List<CourseFormResponseDto> getAllCourse(Integer roleId);

    CourseForm createCourseForm(CourseFormRequestDto courseFormRequestDto);

    CourseFormResponseDto getDetailCourse(Integer idForm);

    void updateCourseForm(Integer idForm, CourseFormRequestDto courseFormRequestDto);
    
    void deleteCourseForm(Integer idForm);

    List<ComponentCourseDto> getComponentByCourseForm(Integer idForm);

    List<EvaluationFormResponseDto> getEvaluationForm(Integer prodiId);

    List<CriteriaEvaluationFormDto> getCriteriaByEvaluationForm(String formType,Integer prodiId);

    List<TypeOfAspectEvaluationDto> getTypeAspectEvaluationForm(String formType,Integer prodiId);

    void updateComponent(List<ComponentCourseDto> componentCourseDtos);

    List<ComponentAndCriteriasDto> getCriteriaComponentByCourseFormId(Integer idForm);

    void updateOrInsertCriteriaComponent(ComponentAndCriteriasDto newCriterias);

    List<RecapitulationCourseDto> getAllRecapitulationByYearAndProdiId(Integer year, Integer prodiId);

    void finalizationAllCourseForm();

    ByteArrayInputStream loadCourse(Integer year, Integer prodiId);
    // void updateComponentCourse(Integer idComponent);

}
