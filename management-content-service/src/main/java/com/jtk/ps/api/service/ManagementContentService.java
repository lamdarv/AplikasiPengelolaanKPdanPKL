package com.jtk.ps.api.service;

import com.jtk.ps.api.dto.*;
import com.jtk.ps.api.model.*;
import com.jtk.ps.api.repository.*;
import com.jtk.ps.api.util.Constant;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class ManagementContentService implements IManagementContentService {

    @Autowired
    @Lazy
    TimelineSettingRepository timelineSettingRepository;

    @Autowired
    @Lazy
    FormSettingRepository formSettingRepository;

    @Autowired
    @Lazy
    RegionRepository regionRepository;

    @Autowired
    @Lazy
    CompetenceRepository competenceRepository;
    
    @Autowired
    @Lazy
    CompetenceTypeRepository competenceTypeRepository;

    @Autowired
    @Lazy
    JobscopeRepository jobscopeRepository;
    
    @Autowired
    @Lazy
    AssessmentAspectRepository assessmentAspectRepository;
    
    @Autowired
    @Lazy
    EvaluationFormRepository evaluationFormRepository;
    
    @Autowired
    @Lazy
    FeedbackQuestionRepository feedbackQuestionRepository;
    
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public TimelineSetting createTimeline(TimelineRequest timelineRequest, Integer prodi) {
        TimelineSetting timelineSetting = new TimelineSetting();
        timelineSetting.setProdiId(prodi);
        timelineSetting.setName(timelineRequest.getName());
        timelineSetting.setStartDate(timelineRequest.getDateStart());
        timelineSetting.setEndDate(timelineRequest.getDateEnd());
        timelineSetting.setDescription(timelineRequest.getDescription());
        return timelineSettingRepository.save(timelineSetting);
    }

    @Override
    public List<TimelineRequest> getTimeline(Integer prodi) {
        List<TimelineRequest> trList = TimelineRequest.getTimeline(timelineSettingRepository.findByProdiId(prodi));
        List<TimelineRequest> trList2 = TimelineRequest.getTimeline(timelineSettingRepository.findByProdiId(2));
        
        trList2.forEach(tr -> {
            trList.add(tr);
        });
        
        return trList;
    }

    @Override
    public TimelineAllProdiResponse getTimeline() {
        TimelineAllProdiResponse timelineAllProdiResponse = new TimelineAllProdiResponse();
        timelineAllProdiResponse.setTimelineD3(TimelineRequest.getTimeline(timelineSettingRepository.findByProdiId(0)));
        timelineAllProdiResponse.setTimelineD4(TimelineRequest.getTimeline(timelineSettingRepository.findByProdiId(1)));

        return timelineAllProdiResponse;
    }

    @Override
    public void updateTimeline(Integer id, TimelineRequest timelineRequest, Integer prodi) {
        Optional<TimelineSetting> timelineSetting = timelineSettingRepository.findById(id);

        timelineSetting.ifPresent(value -> {
            value.setName(timelineRequest.getName());
            value.setStartDate(timelineRequest.getDateStart());
            value.setEndDate(timelineRequest.getDateEnd());
            value.setDescription(timelineRequest.getDescription());
            value.setProdiId(prodi);
            timelineSettingRepository.save(value);
        });
    }

    @Override
    public void deleteTimeline(Integer id) {
        formSettingRepository.updateTimelineSettingToNull(id);
        timelineSettingRepository.deleteById(id);
    }

    @Override
    public List<FormSetting> getFormSubmitTime(Integer prodiId) {
        List<FormSetting> fsList = formSettingRepository.findByProdiId(prodiId);
        List<FormSetting> fsList2 = formSettingRepository.findByProdiId(2);
        
        fsList2.forEach(tr -> {
            fsList.add(tr);
        });
        
        return fsList;
    }

    @Override
    public void updateFormSubmitTime(Integer id, FormSubmitUpdateRequest formSubmitUpdateRequest) {
        Optional<FormSetting> formSetting = formSettingRepository.findById(id);

        formSetting.ifPresent(value -> {
            if (!id.equals(formSubmitUpdateRequest.getIdTimeline())) {
                Optional<TimelineSetting> timelineSetting = timelineSettingRepository.findById(formSubmitUpdateRequest.getIdTimeline());
                timelineSetting.ifPresent(value::setTimelineSetting);
                value.setDayBefore(formSubmitUpdateRequest.getDayBefore());
                formSettingRepository.save(value);
            }
        });

    }

    @Override
    public FormSetting getFormSubmitTimeById(Integer id) {
        Optional<FormSetting> formSetting = formSettingRepository.findById(id);

        return formSetting.orElse(null);
    }


    @Override
    public List<Region> getDomicileLike(String domicileLike) {
        return regionRepository.searchByRegionName(domicileLike);
    }

    @Override
    public Region getDomicileById(Integer idDomicile) {
        return regionRepository.findById(idDomicile).orElse(null);
    }

    @Override
    public Integer getTypeCompetence(Integer id) {
        Optional<Competence> competence = competenceRepository.findById(id);
        return competence.map(value -> value.getCompetenceType().getId()).orElse(null);
    }

    @Override
    public List<CompetenceResponse> getAllCompetence(Integer idProdi) {
        List<Competence> competences = competenceRepository.findByProdiId(idProdi);

        List<CompetenceResponse> competenceResponse = new ArrayList<>();
        for (Competence competence : competences) {
            CompetenceResponse c = new CompetenceResponse();
            c.setId(competence.getId());
            c.setName(competence.getName());
            c.setType(competence.getCompetenceType().getId());
            c.setPicName(competence.getPic());
            competenceResponse.add(c);
        }

        return competenceResponse;
    }
    
    @Override
    public List<CompetenceType> getAllTypeCompetence() {
        return competenceTypeRepository.findAll();
    }

    @Override
    public void createCompetence(
            CompetenceRequest competenceRequest, String picName) {
        Optional<CompetenceType> competenceType = competenceTypeRepository
                .findById(competenceRequest.getIdCompetenceType());
        
        competenceType.ifPresent(ct -> {
           Competence c = new Competence();
           c.setName(competenceRequest.getName());
           c.setPic(picName);
           c.setCompetenceType(ct);
           c.setProdiId(competenceRequest.getProdiId());
           competenceRepository.save(c);
        });
    }
    
    @Override
    public void updateCompetence(
            Integer id, CompetenceRequest competenceRequest, String picName) {
        Optional<Competence> competence = competenceRepository.findById(id);
        
        competence.ifPresent(c -> {
           c.setName(competenceRequest.getName());
           c.setPic(picName);
           
           Optional<CompetenceType> competenceType = competenceTypeRepository
                .findById(competenceRequest.getIdCompetenceType());
           competenceType.ifPresent(c::setCompetenceType);
           
           competenceRepository.save(c);
        });
    }

    @Override
    public void deleteCompetence(Integer id, String cookie) {
        // Delete CV Competence (Participant)
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<CompetenceResponse> response = restTemplate.exchange(
                "http://participant-service/participant/cv/delete-competence/"+ id,
                HttpMethod.DELETE,
                req,
                CompetenceResponse.class);
        
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when deleting CV competence");
        }
        
        // Delete Prerequisite Competence (Company)
        response = restTemplate.exchange(
                "http://company-service/company/prerequisite/delete-competence/"+ id,
                HttpMethod.DELETE,
                req,
                CompetenceResponse.class);

        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when deleting prerequisite competence");
        }
        
        competenceRepository.deleteById(id);
    }
    
    @Override
    public List<Jobscope> getAllJobscope(Integer idProdi) {
        return jobscopeRepository.findByProdiId(idProdi);
    }

    @Override
    public void createJobscope(JobscopeRequest jobscopeRequest, String picName) {
        Jobscope js = new Jobscope();
        js.setName(jobscopeRequest.getName());
        js.setPic(picName);
        js.setProdiId(jobscopeRequest.getProdiId());
        jobscopeRepository.save(js);
    }

    @Override
    public void updateJobscope(Integer id, JobscopeRequest jobscopeRequest, String picName) {
        Optional<Jobscope> jobscope = jobscopeRepository.findById(id);
        
        jobscope.ifPresent(js -> {
           js.setName(jobscopeRequest.getName());
           js.setPic(picName);
           
           jobscopeRepository.save(js);
        });
    }

    @Override
    public void deleteJobscope(Integer id, String cookie) {
        // Delete CV Jobscope (Participant)
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.PayloadResponseConstant.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<CompetenceResponse> response = restTemplate.exchange(
                "http://participant-service/participant/cv/delete-jobscope/"+ id,
                HttpMethod.DELETE,
                req,
                CompetenceResponse.class);
        
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when deleting CV jobscope");
        }
        
        // Delete Prerequisite Jobscope (Company)
        response = restTemplate.exchange(
                "http://company-service/company/prerequisite/delete-jobscope/"+ id,
                HttpMethod.DELETE,
                req,
                CompetenceResponse.class);

        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            throw new IllegalStateException("Error when deleting prerequisite jobscope");
        }
        
        jobscopeRepository.deleteById(id);
    }
    
    @Override
    public List<String> getAssessmentAspectForm(Integer idProdi, Integer numEvaluation) {
        List<String> resList = new ArrayList<>();
        
        List<AssessmentAspect> aaList = assessmentAspectRepository.findByProdiIdAndNumEvaluation(idProdi, numEvaluation);
        for (AssessmentAspect aa : aaList) {
            resList.add(aa.getAspectName());
        }
        
        return resList;
    }

    @Override
    public List<AssessmentAspect> getAssessmentAspectD3() {
        return assessmentAspectRepository.findByProdiIdAndNumEvaluation(EProdi.D3.id, 1);
    }

    @Override
    public AssessmentAspectD4Response getAssessmentAspectD4() {
        AssessmentAspectD4Response aaRes = new AssessmentAspectD4Response();
        
        aaRes.setEvaluation1(assessmentAspectRepository.findByProdiIdAndNumEvaluation(EProdi.D4.id, 1));
        aaRes.setEvaluation2(assessmentAspectRepository.findByProdiIdAndNumEvaluation(EProdi.D4.id, 2));
        aaRes.setEvaluation3(assessmentAspectRepository.findByProdiIdAndNumEvaluation(EProdi.D4.id, 3));
        
        return aaRes;
    }

    @Override
    public void createAssessmentAspect(AssessmentAspectRequest assessmentAspectRequest) {
        AssessmentAspect aa = new AssessmentAspect();
        aa.setAspectName(assessmentAspectRequest.getAspectName());
        evaluationFormRepository.findById(assessmentAspectRequest.getEvaluationFormId()).ifPresent(ef -> {
           aa.setEvaluationForm(ef);
           assessmentAspectRepository.save(aa);
        });
    }

    @Override
    public void updateAssessmentAspect(Integer id, AssessmentAspectRequest assessmentAspectRequest) {
        assessmentAspectRepository.findById(id).ifPresent(aa -> {
            aa.setAspectName(assessmentAspectRequest.getAspectName());
            assessmentAspectRepository.save(aa);
        });
    }

    @Override
    public void deleteAssessmentAspect(Integer id) {
        assessmentAspectRepository.deleteById(id);
    }

    public List<FeedbackQuestion> getAllFeedbackQuestion(Integer idProdi) {
        return feedbackQuestionRepository.findByProdiId(idProdi);
    }

    @Override
    public void createFeedbackQuestion(Integer idProdi, FeedbackQuestionRequest fqRequest) {
        FeedbackQuestion fq = new FeedbackQuestion();
        fq.setQuestion(fqRequest.getQuestion());
        fq.setProdiId(idProdi);
        feedbackQuestionRepository.save(fq);
    }

    @Override
    public void updateFeedbackQuestion(Integer id, FeedbackQuestionRequest fqRequest) {
        feedbackQuestionRepository.findById(id).ifPresent(fq -> {
            fq.setQuestion(fqRequest.getQuestion());
            feedbackQuestionRepository.save(fq);
        });
    }

    @Override
    public void deleteFeedbackQuestion(Integer id) {
        feedbackQuestionRepository.deleteById(id);
    }
}