package com.jtk.ps.api.service;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.FormSetting;
import com.jtk.ps.api.model.Jobscope;
import com.jtk.ps.api.model.Region;
import com.jtk.ps.api.model.TimelineSetting;
import com.jtk.ps.api.model.AssessmentAspect;
import com.jtk.ps.api.model.FeedbackQuestion;
import com.jtk.ps.api.dto.CompetenceRequest;
import com.jtk.ps.api.dto.JobscopeRequest;
import com.jtk.ps.api.dto.FeedbackQuestionRequest;
import com.jtk.ps.api.model.CompetenceType;

import java.util.List;

public interface IManagementContentService {
    TimelineSetting createTimeline(TimelineRequest timelineRequest, Integer prodi);

    List<TimelineRequest> getTimeline(Integer prodi);

    TimelineAllProdiResponse getTimeline();

    void updateTimeline(Integer id,TimelineRequest timelineRequest, Integer prodi);

    void deleteTimeline(Integer id);

    List<FormSetting> getFormSubmitTime(Integer prodiId);

    void updateFormSubmitTime(Integer id, FormSubmitUpdateRequest formSubmitUpdateRequest);

    FormSetting getFormSubmitTimeById(Integer id);

    List<Region> getDomicileLike(String domicileLike);

    Region getDomicileById(Integer idDomicile);

    Integer getTypeCompetence(Integer id);

    List<CompetenceResponse> getAllCompetence(Integer idProdi);
    
    List<CompetenceType> getAllTypeCompetence();

    List<Jobscope> getAllJobscope(Integer idProdi);

    void createCompetence(CompetenceRequest competenceRequest, String picName);
    
    void updateCompetence(Integer id, CompetenceRequest competenceRequest, String picName);
    
    void deleteCompetence(Integer id, String cookie);
    
    void createJobscope(JobscopeRequest jobscopeRequest, String picName);
    
    void updateJobscope(Integer id, JobscopeRequest jobscopeRequest, String picName);
    
    void deleteJobscope(Integer id, String cookie);
    
    List<String> getAssessmentAspectForm(Integer idProdi, Integer numEvaluation);
    
    List<AssessmentAspect> getAssessmentAspectD3();
    
    AssessmentAspectD4Response getAssessmentAspectD4();
    
    void createAssessmentAspect(AssessmentAspectRequest assessmentAspectRequest);
    
    void updateAssessmentAspect(Integer id, AssessmentAspectRequest assessmentAspectRequest);
    
    void deleteAssessmentAspect(Integer id);
    
    List<FeedbackQuestion> getAllFeedbackQuestion(Integer idProdi);
    
    void createFeedbackQuestion(Integer idProdi, FeedbackQuestionRequest fqRequest);
    
    void updateFeedbackQuestion(Integer id, FeedbackQuestionRequest fqRequest);
    
    void deleteFeedbackQuestion(Integer id);
}
